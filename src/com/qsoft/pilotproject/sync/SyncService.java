package com.qsoft.pilotproject.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * User: binhtv
 * Date: 11/1/13
 * Time: 9:58 AM
 */
public class SyncService extends Service
{
    private static final String TAG = "SyncService";
    private static final Object syncAdapterLock = new Object();
    private static SyncAdapter syncAdapter = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "service created...");
        synchronized (syncAdapterLock)
        {
            if (syncAdapter == null)
            {
                syncAdapter = new SyncAdapter(getApplicationContext());
            }
        }
    }

    public IBinder onBind(Intent intent)
    {
        return syncAdapter.getSyncAdapterBinder();
    }


    @Override
    /**
     * Logging-only destructor.
     */
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }
}
