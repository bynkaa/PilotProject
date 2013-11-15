package com.qsoft.pilotproject.sync;

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
import com.qsoft.pilotproject.authenticator.InvalidTokenException;
import com.qsoft.pilotproject.model.cc.CommentCC;
import com.qsoft.pilotproject.model.cc.CommentCCContract;
import com.qsoft.pilotproject.model.cc.FeedCC;
import com.qsoft.pilotproject.model.cc.FeedCCContract;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.provider.cc.CCContract;
import com.qsoft.pilotproject.rest.OnlineDioClientProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 10:58 AM
 */
@EBean
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
    private static final String TAG = "SyncAdapter";
    private static final String[] FEED_PROJECTION = new String[]
            {
//                    OnlineDioContract.Feed._ID,
//                    OnlineDioContract.Feed.COLUMN_AVATAR,
//                    OnlineDioContract.Feed.COLUMN_COMMENTS,
//                    OnlineDioContract.Feed.COLUMN_CREATED_AT,
//                    OnlineDioContract.Feed.COLUMN_DESCRIPTION,
//                    OnlineDioContract.Feed.COLUMN_DISPLAY_NAME,
//                    OnlineDioContract.Feed.COLUMN_DURATION,
//                    OnlineDioContract.Feed.COLUMN_ID,
//                    OnlineDioContract.Feed.COLUMN_LIKES,
//                    OnlineDioContract.Feed.COLUMN_PLAYED,
//                    OnlineDioContract.Feed.COLUMN_SOUND_PATH,
//                    OnlineDioContract.Feed.COLUMN_THUMBNAIL,
//                    OnlineDioContract.Feed.COLUMN_TITLE,
//                    OnlineDioContract.Feed.COLUMN_UPDATED_AT,
//                    OnlineDioContract.Feed.COLUMN_USER_ID,
//                    OnlineDioContract.Feed.COLUMN_USER_NAME,
//                    OnlineDioContract.Feed.COLUMN_VIEWED,
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
                    OnlineDioContract.Comment._ID,
                    OnlineDioContract.Comment.COLUMN_ID,
                    OnlineDioContract.Comment.COLUMN_USER_ID,
                    OnlineDioContract.Comment.COLUMN_USER_NAME,
                    OnlineDioContract.Comment.COLUMN_CONTENT,
                    OnlineDioContract.Comment.COLUMN_DISPLAY_NAME,
                    OnlineDioContract.Comment.COLUMN_AVATAR,
                    OnlineDioContract.Comment.COLUMN_CREATED_AT,
                    OnlineDioContract.Comment.COLUMN_SOUND_ID,
                    OnlineDioContract.Comment.COLUMN_UPDATED_AT
            };

    Context context;
    @Bean
    OnlineDioClientProxy onlineDioClientProxy;

    AccountManager accountManager;

    public SyncAdapter(Context context)
    {
        super(context, true);
        this.context = context;
        accountManager = AccountManager.get(context);
    }

//    private SyncHelper syncHelper;

    @Override
    public void onPerformSync(Account account, Bundle bundle, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
        Log.d(TAG, "onPerformSync()");
        try
        {
            updateLocalFeedData(account, syncResult);
//            updateLocalCommentData(account, syncResult);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (OperationApplicationException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (InvalidTokenException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void updateLocalFeedData(Account account, SyncResult syncResult) throws RemoteException, OperationApplicationException, InvalidTokenException, InterruptedException
    {
        final ContentResolver contentResolver = getContext().getContentResolver();
        Log.d(TAG, "get list feeds from server");
//        FeedHandler feedHandler = FeedHandlerImpl_.getInstance_(context);
//        List<FeedDTO> remoteFeeds = feedHandler.getFeeds(accountManager, account);
        List<FeedCC> remoteFeeds = onlineDioClientProxy.getFeeds("", "", "", "");
        Log.d(TAG, "parsing complete. Found : " + remoteFeeds.size());
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<Long, FeedCC> feedMap = new HashMap<Long, FeedCC>();
        for (FeedCC feedCC : remoteFeeds)
        {
            feedMap.put(feedCC.getFeedId(), feedCC);
        }
        // get list of all items
        Log.d(TAG, "Fetching local feed for merge");
        Uri uri = FeedCCContract.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, FEED_PROJECTION, null, null, null);
        assert cursor != null;
        Log.i(TAG, "Found " + cursor.getCount() + " local feeds");
        while (cursor.moveToNext())
        {
            syncResult.stats.numEntries++;
            FeedCC feed = FeedCC.fromCursor(cursor);
            FeedCC match = feedMap.get(feed.getFeedId());
            if (match != null)
            {
                feedMap.remove(feed.getFeedId());
                Uri existingUri = FeedCCContract.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(feed.getId())).build();
                if (match.getUpdatedAt() != null && !match.getUpdatedAt().equals(feed.getUpdatedAt())
                        || match.getLikes() != feed.getLikes() || match.getComments() != feed.getComments())
                {
                    Log.d(TAG, "Scheduling update: " + existingUri);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValues(match.getContentValues()).build());
                    syncResult.stats.numUpdates++;
                }
                else
                {
                    Log.i(TAG, "sync perform: No action");
                }
            }
            else
            {
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
        for (FeedCC feedCC : feedMap.values())
        {
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
    void showMessage()
    {
        Toast.makeText(context, "abc", Toast.LENGTH_LONG).show();
    }

    private void updateLocalCommentData(Account account, SyncResult syncResult)
    {
        final ContentResolver contentResolver = getContext().getContentResolver();
        Log.d(TAG, "get list feeds from server");
//        List<CommentDTO> remoteComments = commentHandler.getListComments(accountManager, account, 161L);
        List<CommentCC> remoteComments = onlineDioClientProxy.getComments(161L, "", "", "");
        Log.d(TAG, "parsing complete. Found : " + remoteComments.size());
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<Long, CommentCC> commentMap = new HashMap<Long, CommentCC>();
        for (CommentCC commentDTO : remoteComments)
        {
            commentMap.put(commentDTO.getCommentId(), commentDTO);
        }
        // get list of all items
        Log.d(TAG, "Fetching local feed for merge");
        Uri uri = CommentCCContract.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, COMMENT_PROJECTION, null, null, null);
        assert cursor != null;
        Log.i(TAG, "Found " + cursor.getCount() + " local feeds");
        while (cursor.moveToNext())
        {
            syncResult.stats.numEntries++;
            CommentCC comment = CommentCC.fromCursor(cursor);
            CommentCC match = commentMap.get(comment.getCommentId());
            if (match != null)
            {
                commentMap.remove(comment.getCommentId());
                Uri existingUri = CommentCCContract.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(comment.getId())).build();
                if (match.getUpdatedAt() != null && !match.getUpdatedAt().equals(comment.getUpdatedAt()))
                {
                    Log.d(TAG, "Scheduling update: " + existingUri);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValues(match.getContentValues()).build());
                    syncResult.stats.numUpdates++;
                }
                else
                {
                    Log.i(TAG, "sync perform: No action");
                }
            }
            else
            {
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
        for (CommentCC commentDTO : commentMap.values())
        {
            Log.i(TAG, "scheduling insert: entry_id=" + commentDTO.getCommentId());
            batch.add(ContentProviderOperation.newInsert(CommentCCContract.CONTENT_URI)
                    .withValues(commentDTO.getContentValues()).build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        try
        {
            contentResolver.applyBatch(CCContract.AUTHORITY, batch);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (OperationApplicationException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        contentResolver.notifyChange(CommentCCContract.CONTENT_URI, null, false);

    }

}
