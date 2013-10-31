package com.qsoft.pilotproject.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
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

    private SyncHelper syncHelper;

    @Override
    public void onPerformSync(Account account, Bundle bundle, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
        Log.d(TAG, "onPerformSync()");
        try
        {
            String authToken = accountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
            Log.d(TAG, "onPerformSync() > Get remote Feeds");
            FeedHandler feedHandler = new FeedHandlerImpl();
            List<FeedDTO> remoteFeeds = feedHandler.getFeeds();
            Log.d(TAG, "onPerformSync() > Get local Feeds");
            List<FeedDTO> localFeeds = new ArrayList<FeedDTO>();
            Cursor cursorFeed = provider.query(OnlineDioContract.Feed.CONTENT_URI, null, null, null, null);
            if (cursorFeed != null)
            {
                while (cursorFeed.moveToNext())
                {
                    // get feed from cursor
                }
            }
        }
        catch (AuthenticatorException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (OperationCanceledException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (RemoteException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
