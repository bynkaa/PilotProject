package com.qsoft.pilotproject.data.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.pilotproject.data.model.entity.SyncToServer;
import com.qsoft.pilotproject.data.model.entity.SyncToServerContract;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Qsoft
 * Date: 11/20/13
 * Time: 3:35 PM
 */
@EBean
public class SyncToServiceDAO
{
    @RootContext
    Context context;
    ContentResolver contentResolver;

    @AfterInject
    void afterInject()
    {
        contentResolver = context.getContentResolver();
    }

    public void insertOrUpdateToSync(SyncToServer syncToServer)
    {
        String where = SyncToServerContract.TABLENAME + "=?" + " OR " + SyncToServerContract.RECORDID + "=?";
        String[] args = new String[]{syncToServer.getTableName(), syncToServer.getRecordId().toString()};
        Cursor cursor = contentResolver.query(SyncToServerContract.CONTENT_URI,
                new String[]{SyncToServerContract._ID}, where, args, null);
        if (cursor.moveToFirst())
        {
            Long id = cursor.getLong(0);
            contentResolver.update(SyncToServerContract.CONTENT_URI, syncToServer.getContentValues(),
                    SyncToServerContract._ID + "=?", new String[]{id.toString()});
        }
        else
        {
            contentResolver.insert(SyncToServerContract.CONTENT_URI, syncToServer.getContentValues());
        }

    }

    public List<SyncToServer> getAllData()
    {
        String selection = SyncToServerContract.STATUS + "<>?";
        String[] selectionArgs = new String[]{"synchronized"};
        Cursor cursor = contentResolver.query(SyncToServerContract.CONTENT_URI, null, selection, selectionArgs, SyncToServerContract.PRIORITY);
        List<SyncToServer> syncToServers = new ArrayList<SyncToServer>();
        while (cursor.moveToNext())
        {
            SyncToServer syncToServer = SyncToServer.fromCursor(cursor);
            syncToServers.add(syncToServer);
        }
        return syncToServers;
    }
}
