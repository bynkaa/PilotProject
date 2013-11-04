package com.qsoft.pilotproject.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.handler.FeedHandler;
import com.qsoft.pilotproject.handler.impl.FeedHandlerImpl;
import com.qsoft.pilotproject.model.Feed;
import com.qsoft.pilotproject.model.dto.FeedDTO;
import com.qsoft.pilotproject.provider.OnlineDioContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 10:58 AM
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
    private static final String TAG = "SyncAdapter";
    private static final String[] PROJECTION = new String[]
            {
                    OnlineDioContract.Feed._ID,
                    OnlineDioContract.Feed.COLUMN_AVATAR,
                    OnlineDioContract.Feed.COLUMN_COMMENTS,
                    OnlineDioContract.Feed.COLUMN_CREATED_AT,
                    OnlineDioContract.Feed.COLUMN_DESCRIPTION,
                    OnlineDioContract.Feed.COLUMN_DISPLAY_NAME,
                    OnlineDioContract.Feed.COLUMN_DURATION,
                    OnlineDioContract.Feed.COLUMN_ID,
                    OnlineDioContract.Feed.COLUMN_LIKES,
                    OnlineDioContract.Feed.COLUMN_PLAYED,
                    OnlineDioContract.Feed.COLUMN_SOUND_PATH,
                    OnlineDioContract.Feed.COLUMN_THUMBNAIL,
                    OnlineDioContract.Feed.COLUMN_TITLE,
                    OnlineDioContract.Feed.COLUMN_UPDATED_AT,
                    OnlineDioContract.Feed.COLUMN_USER_ID,
                    OnlineDioContract.Feed.COLUMN_USER_NAME,
                    OnlineDioContract.Feed.COLUMN_VIEWED,
            };
    private final Context context;
    private final AccountManager accountManager;

    public SyncAdapter(Context context, boolean autoIntitialize)
    {
        super(context, autoIntitialize);
        this.context = context;
        accountManager = AccountManager.get(context);
    }

//    private SyncHelper syncHelper;

    @Override
    public void onPerformSync(Account account, Bundle bundle, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
        Log.d(TAG, "onPerformSync()");
        String authToken = accountManager.peekAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
//            Log.d(TAG, "onPerformSync() > Get remote Feeds");
//            FeedHandler feedHandler = new FeedHandlerImpl();
//            List<FeedDTO> remoteFeeds = feedHandler.getFeeds(authToken);
//            Log.d(TAG, "onPerformSync() > Get local Feeds");
//            List<FeedDTO> localFeeds = new ArrayList<FeedDTO>();
//            Cursor cursorFeed = provider.query(OnlineDioContract.Feed.CONTENT_URI, null, null, null, null);
//            if (cursorFeed != null)
//            {
//                while (cursorFeed.moveToNext())
//                {
//                    // get feed from cursor
//                    localFeeds.add(FeedDTO.fromCursor(cursorFeed));
//                }
//                cursorFeed.close();
//            }
//
//            // see what remote feeds are missing on Remote
//            ArrayList<FeedDTO> feedsToRemote = new ArrayList<FeedDTO>();
//            for (FeedDTO feedDTO : localFeeds)
//            {
//                if (!remoteFeeds.contains(feedDTO))
//                {
//                    feedsToRemote.add(feedDTO);
//                }
//            }
//            if (feedsToRemote.size() == 0)
//            {
//                Log.d(TAG, "No Local changes to update server");
//            }
//            else
//            {
//                Log.d(TAG, " updating remote server with local change");
//            }
//
//            // see what remote feeds are missing on Local
//            ArrayList<FeedDTO> feedsToLocal = new ArrayList<FeedDTO>();
//            for (FeedDTO feedDTO : remoteFeeds)
//            {
//                if (!localFeeds.contains(feedDTO))
//                {
//                    feedsToLocal.add(feedDTO);
//                }
//            }
//            if (feedsToLocal.size() == 0)
//            {
//                Log.d(TAG, "No server changes to update local database");
//            }
//            else
//            {
//                Log.d(TAG, " updating local database with remote change");
//                int i = 0;
//                ContentValues feedsToLocalValues[] = new ContentValues[feedsToLocal.size()];
//                for (FeedDTO localFeed : feedsToLocal)
//                {
//                    Log.d(TAG, " remote -> local");
//                    feedsToLocalValues[i++] = localFeed.getContentValues();
//                }
//                provider.bulkInsert(OnlineDioContract.Feed.CONTENT_URI, feedsToLocalValues);
//            }
//            Log.d(TAG, "sync finished");
        try
        {
            updateLocalFeedData(authToken, syncResult);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (OperationApplicationException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    private void updateLocalFeedData(String authToken, SyncResult syncResult) throws RemoteException, OperationApplicationException
    {
        final ContentResolver contentResolver = getContext().getContentResolver();
        Log.d(TAG, "get list feeds from server");
        FeedHandler feedHandler = new FeedHandlerImpl();
        List<FeedDTO> remoteFeeds = feedHandler.getFeeds(authToken);
        Log.d(TAG, "parsing complete. Found : " + remoteFeeds.size());
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<Long, FeedDTO> feedMap = new HashMap<Long, FeedDTO>();
        for (FeedDTO feedDTO : remoteFeeds)
        {
            feedMap.put(feedDTO.getFeedId(), feedDTO);
        }
        // get list of all items
        Log.d(TAG, "Fetching local feed for merge");
        Uri uri = OnlineDioContract.Feed.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, PROJECTION, null, null, null);
        assert cursor != null;
        Log.i(TAG, "Found " + cursor.getCount() + " local feeds");
        while (cursor.moveToNext())
        {
            syncResult.stats.numEntries++;
            Feed feed = Feed.fromCursor(cursor);
            FeedDTO match = feedMap.get(feed.getFeedId());
            if (match != null)
            {
                feedMap.remove(feed.getFeedId());
                Uri existingUri = OnlineDioContract.Feed.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(feed.getId())).build();
                if (match.getUpdatedAt() != null && !match.getUpdatedAt().equals(feed.getUpdatedAt())
                        || match.getLikes() != feed.getLikes())
                {
                    Log.d(TAG, "Scheduling update: " + existingUri);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValues(feed.getContentValues()).build());
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
                Uri deleteUri = OnlineDioContract.Feed.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(feed.getId())).build();
                Log.i(TAG, "scheduling delete: " + deleteUri);
                batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        cursor.close();
        // add new items
        for (FeedDTO feedDTO : feedMap.values())
        {
            Log.i(TAG, "scheduling insert: entry_id=" + feedDTO.getFeedId());
            batch.add(ContentProviderOperation.newInsert(OnlineDioContract.Feed.CONTENT_URI)
                    .withValues(feedDTO.getContentValues()).build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        contentResolver.applyBatch(OnlineDioContract.CONTENT_AUTHORITY, batch);
        contentResolver.notifyChange(OnlineDioContract.Feed.CONTENT_URI, null, false);


    }
}
