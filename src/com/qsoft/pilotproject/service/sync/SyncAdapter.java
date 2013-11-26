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
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.UiThread;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.utils.Utilities;
import com.qsoft.pilotproject.config.AppSetting;
import com.qsoft.pilotproject.data.model.dto.FeedDTO;
import com.qsoft.pilotproject.data.model.dto.ProfileDTO;
import com.qsoft.pilotproject.data.model.entity.*;
import com.qsoft.pilotproject.data.provider.CCContract;
import com.qsoft.pilotproject.data.rest.*;
import com.qsoft.pilotproject.service.SyncDataServer;

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
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
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
    InterceptorDecoratorFactory interceptorDecoratorFactory;
    @Bean
    SyncDataServer syncDataServer;
    @Bean
    ApplicationAccountManager applicationAccountManager;

    AccountManager accountManager;
    ContentResolver contentResolver;

    @AfterInject
    void init()
    {
        contentResolver = getContext().getContentResolver();

    }

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

            Integer flag = bundle.getInt(AppSetting.SYNC_FLAG);
            if (flag == 0 || flag == null)
            {
                getDataFromServer(account, syncResult, authority);
                syncDataServer.performSync();
            }
            else if (flag == 1)
            {
                getDataFromServer(account, syncResult, authority);

            }
            else if (flag == 2)
            {
                syncDataServer.performSync();
            }

        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (OperationApplicationException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void getDataFromServer(Account account, SyncResult syncResult, String authority) throws Exception
    {
        getFeedFromServer(account, syncResult, authority);
        getProfileFromServer(account, syncResult, authority);
//        updateLocalCommentData(account, syncResult);
    }

    private void getProfileFromServer(Account account, SyncResult syncResult, String authority) throws Exception
    {
        Log.d(TAG, "get profile from server");
        ProfileRestClientCustom profileRestClientCustom = (ProfileRestClientCustom) SingletonFactoryHolder.getSingleton(ProfileRestClient.class);
        profileRestClientCustom = (ProfileRestClientCustom) interceptorDecoratorFactory.wrap(profileRestClientCustom);
        ProfileDTO remoteProfile = profileRestClientCustom.get(applicationAccountManager.getUserId()).getProfile();

        Log.d(TAG, "fetching local profile for merger");
        String selection = ProfileCCContract.USERID + "=?";
        String[] selectionArgs = new String[]{applicationAccountManager.getUserId().toString()};
        Cursor cursor = contentResolver.query(ProfileCCContract.CONTENT_URI, new String[]{ProfileCCContract._ID},
                selection, selectionArgs, null);
        ProfileCC newProfile = new ProfileCC();
        Utilities.copyProperties(newProfile, remoteProfile);
        if (cursor.moveToFirst())
        {
            Long id = cursor.getLong(cursor.getColumnIndex(ProfileCCContract._ID));

            contentResolver.update(ProfileCCContract.CONTENT_URI, newProfile.getContentValues(), ProfileCCContract._ID + "=?", new String[]{id.toString()});
        }
        else
        {
            contentResolver.insert(ProfileCCContract.CONTENT_URI, newProfile.getContentValues());
        }
        contentResolver.notifyChange(ProfileCCContract.CONTENT_URI, null, false);

    }

    private void getFeedFromServer(Account account, SyncResult syncResult, String authority) throws Exception
    {
        Log.d(TAG, "get list feeds from server");
        //get updated date from preference
        SharedPreferences preferences = getContext().getSharedPreferences("", Context.MODE_PRIVATE);
        String updatedDate = preferences.getString(account.name + "_" + authority, "");
        //get ofset, limit from setting

        //todo
        FeedRestClientCustom feedRestClientCustom = (FeedRestClientCustom) SingletonFactoryHolder.getSingleton(FeedRestClient.class);
        feedRestClientCustom = (FeedRestClientCustom) interceptorDecoratorFactory.wrap(feedRestClientCustom);
        List<FeedDTO> remoteFeeds = feedRestClientCustom.getFeeds(AppSetting.SERVICE_PAGING + "", "", updatedDate, "").getFeedDTOs();
        if (remoteFeeds == null || remoteFeeds.size() == 0)
        {
            return;
        }

        Date lastUpdated = Utilities.convertStringToTimeStamp(remoteFeeds.get(0).getUpdatedAt());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(account.name + "_" + authority, lastUpdated.toString());
        editor.commit();
        Log.d(TAG, "parsing complete. Found : " + remoteFeeds.size());
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<Long, FeedDTO> feedMap = new HashMap<Long, FeedDTO>();
        for (FeedDTO feedDTO : remoteFeeds)
        {
            feedMap.put(feedDTO.getFeedId(), feedDTO);
        }

        // get list of all items
        Log.d(TAG, "Fetching local feed for merge");
        Uri uri = FeedCCContract.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, FEED_PROJECTION, null, null, null);
        assert cursor != null;
        Log.i(TAG, "Found " + cursor.getCount() + " local feeds");

        //compare local and server data
        while (cursor.moveToNext())
        {
            syncResult.stats.numEntries++;
            FeedCC feed = FeedCC.fromCursor(cursor);

            FeedDTO match = feedMap.get(feed.getFeedId());
            if (match != null)
            {
                feedMap.remove(feed.getFeedId());
                Uri existingUri = FeedCCContract.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(feed.get_id())).build();
                //Check updated date field
                if (match.getUpdatedAt() != null && !match.getUpdatedAt().equals(feed.getUpdatedAt())
                        || match.getLikes() != feed.getLikes() || match.getComments() != feed.getComments())
                {
                    Log.d(TAG, "Scheduling update: " + existingUri);
                    FeedCC feedCC = new FeedCC();
                    Utilities.copyProperties(feedCC, match);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValues(feedCC.getContentValues()).build());
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
                        .appendPath(Long.toString(feed.get_id())).build();
                Log.i(TAG, "scheduling delete: " + deleteUri);
                batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }

        }
        showMessage();
        cursor.close();
        // add new items
        for (FeedDTO feedDTO : feedMap.values())
        {
            Log.i(TAG, "scheduling insert: entry_id=" + feedDTO.getFeedId());
            FeedCC feedCC = new FeedCC();
            Utilities.copyProperties(feedCC, feedDTO);
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

    private void getCommentFromServer(Account account, SyncResult syncResult)
    {
        Log.d(TAG, "get list feeds from server");
//        List<CommentCC> remoteComments = interceptorDecoratorFactory.getComments(161L, "", "", "");
        List<CommentCC> remoteComments = null;
        Log.d(TAG, "parsing complete. Found : " + remoteComments.size());
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<Long, CommentCC> commentMap = new HashMap<Long, CommentCC>();
        for (CommentCC comment : remoteComments)
        {
            commentMap.put(comment.getCommentId(), comment);
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
