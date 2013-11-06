package com.qsoft.pilotproject.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.googlecode.androidannotations.annotations.EBean;
import com.qsoft.pilotproject.authenticator.InvalidTokenException;
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
@EBean
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
        try
        {
            updateLocalFeedData(account, syncResult);
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


    }

    private void updateLocalFeedData(Account account, SyncResult syncResult) throws RemoteException, OperationApplicationException, InvalidTokenException
    {
        final ContentResolver contentResolver = getContext().getContentResolver();
        Log.d(TAG, "get list feeds from server");
        FeedHandler feedHandler = new FeedHandlerImpl();
        List<FeedDTO> remoteFeeds = feedHandler.getFeeds(accountManager, account);
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
