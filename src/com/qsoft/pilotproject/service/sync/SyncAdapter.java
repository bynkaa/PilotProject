package com.qsoft.pilotproject.service.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.UiThread;
import com.qsoft.pilotproject.common.authenticator.InvalidTokenException;
import com.qsoft.pilotproject.common.utils.Utilities;
import com.qsoft.pilotproject.config.AppSetting;
import com.qsoft.pilotproject.data.model.entity.CommentCC;
import com.qsoft.pilotproject.data.model.entity.CommentCCContract;
import com.qsoft.pilotproject.data.model.entity.FeedCC;
import com.qsoft.pilotproject.data.model.entity.FeedCCContract;
import com.qsoft.pilotproject.data.provider.CCContract;
import com.qsoft.pilotproject.data.rest.OnlineDioClientProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 10:58 AM
 */
@EBean
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = "SyncAdapter";
    private static final String[] FEED_PROJECTION = new String[]
            {
                    FeedCCContract._ID,
                    FeedCCContract.AVATAR,
                    FeedCCContract.COMMENTS,
                    FeedCCContract.CREATEDAT,
                    FeedCCContract.DESCRIPTION,
                    FeedCCContract.DISPLAYNAME,
                    FeedCCContract.DURATION,
                    FeedCCContract.FEEDID,
                    FeedCCContract.LIKES,
                    FeedCCContract.PLAYED,
                    FeedCCContract.SOUNDPATH,
                    FeedCCContract.THUMBNAIL,
                    FeedCCContract.TITLE,
                    FeedCCContract.UPDATEDAT,
                    FeedCCContract.USERID,
                    FeedCCContract.USERNAME,
                    FeedCCContract.VIEWED
            };
    private static final String[] COMMENT_PROJECTION = new String[]
            {
                    CommentCCContract._ID,
                    CommentCCContract.COMMENTID,
                    CommentCCContract.USERID,
                    CommentCCContract.USERNAME,
                    CommentCCContract.COMMENT,
                    CommentCCContract.DISPLAYNAME,
                    CommentCCContract.AVATAR,
                    CommentCCContract.CREATEDAT,
                    CommentCCContract.SOUNDID,
                    CommentCCContract.UPDATEDAT
            };

    Context context;
    @Bean
    OnlineDioClientProxy onlineDioClientProxy;

    AccountManager accountManager;

    public SyncAdapter(Context context) {
        super(context, true);
        this.context = context;
        accountManager = AccountManager.get(context);
    }

//    private SyncHelper syncHelper;

    @Override
    public void onPerformSync(Account account, Bundle bundle, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "onPerformSync()");
        try {
            updateLocalFeedData(account, syncResult, authority);
            updateLocalCommentData(account, syncResult);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateLocalFeedData(Account account, SyncResult syncResult, String authority) throws RemoteException, OperationApplicationException, InvalidTokenException, InterruptedException {
        final ContentResolver contentResolver = getContext().getContentResolver();

        Log.d(TAG, "get list feeds from server");

        //get updated date from preference
        SharedPreferences preferences = getContext().getSharedPreferences("", Context.MODE_PRIVATE);
        String updatedDate = preferences.getString(account.name + "_" + authority, "");
        //get ofset, limit from setting

        List<FeedCC> remoteFeeds = onlineDioClientProxy.getFeeds(AppSetting.SERVICE_PAGING + "", "", updatedDate, "");
        if (remoteFeeds != null) {
            Date lastUpdated = Utilities.convertStringToTimeStamp(remoteFeeds.get(0).getUpdatedAt());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(account.name + "_" + authority, lastUpdated.toString());
            editor.commit();
        }
        Log.d(TAG, "parsing complete. Found : " + remoteFeeds.size());
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<Long, FeedCC> feedMap = new HashMap<Long, FeedCC>();
        for (FeedCC feedCC : remoteFeeds) {
            feedMap.put(feedCC.getFeedId(), feedCC);
        }

        // get list of all items
        Log.d(TAG, "Fetching local feed for merge");
        Uri uri = FeedCCContract.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, FEED_PROJECTION, null, null, null);
        assert cursor != null;
        Log.i(TAG, "Found " + cursor.getCount() + " local feeds");

        //compare local and server data
        while (cursor.moveToNext()) {
            syncResult.stats.numEntries++;
            FeedCC feed = FeedCC.fromCursor(cursor);

            FeedCC match = feedMap.get(feed.getFeedId());
            if (match != null) {
                feedMap.remove(feed.getFeedId());
                Uri existingUri = FeedCCContract.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(feed.getId())).build();
                //Check updated date field
                if (match.getUpdatedAt() != null && !match.getUpdatedAt().equals(feed.getUpdatedAt())
                        || match.getLikes() != feed.getLikes() || match.getComments() != feed.getComments()) {
                    Log.d(TAG, "Scheduling update: " + existingUri);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValues(match.getContentValues()).build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "sync perform: No action");
                }
            } else {
                // feed doesn't exist. Remove it from the database
                Uri deleteUri = FeedCCContract.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(feed.getId())).build();
                Log.i(TAG, "scheduling delete: " + deleteUri);
                batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }

        }
        showMessage();
        cursor.close();
        // add new items
        for (FeedCC feedCC : feedMap.values()) {
            Log.i(TAG, "scheduling insert: entry_id=" + feedCC.getFeedId());
            batch.add(ContentProviderOperation.newInsert(FeedCCContract.CONTENT_URI)
                    .withValues(feedCC.getContentValues()).build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        contentResolver.applyBatch(CCContract.AUTHORITY, batch);
        contentResolver.notifyChange(FeedCCContract.CONTENT_URI, null, false);
    }

    @UiThread
    void showMessage() {
        Toast.makeText(context, "abc", Toast.LENGTH_LONG).show();
    }

    private void updateLocalCommentData(Account account, SyncResult syncResult) {
        final ContentResolver contentResolver = getContext().getContentResolver();
        Log.d(TAG, "get list feeds from server");
        List<CommentCC> remoteComments = onlineDioClientProxy.getComments(161L, "", "", "");
        Log.d(TAG, "parsing complete. Found : " + remoteComments.size());
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<Long, CommentCC> commentMap = new HashMap<Long, CommentCC>();
        for (CommentCC comment : remoteComments) {
            commentMap.put(comment.getCommentId(), comment);
        }
        // get list of all items
        Log.d(TAG, "Fetching local feed for merge");
        Uri uri = CommentCCContract.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, COMMENT_PROJECTION, null, null, null);
        assert cursor != null;
        Log.i(TAG, "Found " + cursor.getCount() + " local feeds");
        while (cursor.moveToNext()) {
            syncResult.stats.numEntries++;
            CommentCC comment = CommentCC.fromCursor(cursor);
            CommentCC match = commentMap.get(comment.getCommentId());
            if (match != null) {
                commentMap.remove(comment.getCommentId());
                Uri existingUri = CommentCCContract.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(comment.getId())).build();
                if (match.getUpdatedAt() != null && !match.getUpdatedAt().equals(comment.getUpdatedAt())) {
                    Log.d(TAG, "Scheduling update: " + existingUri);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValues(match.getContentValues()).build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "sync perform: No action");
                }
            } else {
                // feed doesn't exist. Remove it from the database
                Uri deleteUri = CommentCCContract.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(comment.getId())).build();
                Log.i(TAG, "scheduling delete: " + deleteUri);
                batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        cursor.close();
        // add new items
        for (CommentCC commentDTO : commentMap.values()) {
            Log.i(TAG, "scheduling insert: entry_id=" + commentDTO.getCommentId());
            batch.add(ContentProviderOperation.newInsert(CommentCCContract.CONTENT_URI)
                    .withValues(commentDTO.getContentValues()).build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        try {
            contentResolver.applyBatch(CCContract.AUTHORITY, batch);
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (OperationApplicationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        contentResolver.notifyChange(CommentCCContract.CONTENT_URI, null, false);

    }

}
