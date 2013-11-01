package com.qsoft.pilotproject.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.handler.FeedHandler;
import com.qsoft.pilotproject.handler.impl.FeedHandlerImpl;
import com.qsoft.pilotproject.model.dto.FeedDTO;
import com.qsoft.pilotproject.provider.OnlineDioContract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 10:58 AM
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
    private static final String TAG = "SyncAdapter";
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
            String authToken = accountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
            Log.d(TAG, "onPerformSync() > Get remote Feeds");
            FeedHandler feedHandler = new FeedHandlerImpl();
            List<FeedDTO> remoteFeeds = feedHandler.getFeeds(authToken);
            Log.d(TAG, "onPerformSync() > Get local Feeds");
            List<FeedDTO> localFeeds = new ArrayList<FeedDTO>();
            Cursor cursorFeed = provider.query(OnlineDioContract.Feed.CONTENT_URI, null, null, null, null);
            if (cursorFeed != null)
            {
                while (cursorFeed.moveToNext())
                {
                    // get feed from cursor
                    localFeeds.add(FeedDTO.fromCursor(cursorFeed));
                }
                cursorFeed.close();
            }

            // see what remote feeds are missing on Remote
            ArrayList<FeedDTO> feedsToRemote = new ArrayList<FeedDTO>();
            for (FeedDTO feedDTO : localFeeds)
            {
                if (!remoteFeeds.contains(feedDTO))
                {
                    feedsToRemote.add(feedDTO);
                }
            }
            if (feedsToRemote.size() == 0)
            {
                Log.d(TAG, "No Local changes to update server");
            }
            else
            {
                Log.d(TAG, " updating remote server with local change");
            }

            // see what remote feeds are missing on Local
            ArrayList<FeedDTO> feedsToLocal = new ArrayList<FeedDTO>();
            for (FeedDTO feedDTO : remoteFeeds)
            {
                if (!localFeeds.contains(feedDTO))
                {
                    feedsToLocal.add(feedDTO);
                }
            }
            if (feedsToLocal.size() == 0)
            {
                Log.d(TAG, "No server changes to update local database");
            }
            else
            {
                Log.d(TAG, " updating local database with remote change");
                int i = 0;
                ContentValues feedsToLocalValues[] = new ContentValues[feedsToLocal.size()];
                for (FeedDTO localFeed : feedsToLocal)
                {
                    Log.d(TAG, " remote -> local");
                    feedsToLocalValues[i++] = localFeed.getContentValues();
                }
                provider.bulkInsert(OnlineDioContract.Feed.CONTENT_URI, feedsToLocalValues);
            }
            Log.d(TAG, "sync finished");
        }
        catch (AuthenticatorException e)
        {
            e.printStackTrace();
        }
        catch (OperationCanceledException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

    }
}
