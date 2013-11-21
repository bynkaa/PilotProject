package com.qsoft.pilotproject.data.model.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.qsoft.pilotproject.data.provider.CCContract;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
@AdditionalAnnotation.Contract
@DatabaseTable(tableName = "sync_to_server")
@AdditionalAnnotation.DefaultContentUri(authority = CCContract.AUTHORITY, path = "sync_to_server")
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = CCContract.MIME_TYPE_VND, type = "sync_to_server")
public class SyncToServer
{
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    @AdditionalAnnotation.DefaultSortOrder
    Long id;
    @DatabaseField
    String tableName;
    @DatabaseField
    Long recordId;
    @DatabaseField
    int groupS;
    @DatabaseField
    int priority;
    @DatabaseField
    String action;
    @DatabaseField
    String status;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public Long getRecordId()
    {
        return recordId;
    }

    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

    public Integer getGroupS()
    {
        return groupS;
    }

    public void setGroupS(Integer groupS)
    {
        this.groupS = groupS;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public ContentValues getContentValues()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SyncToServerContract._ID, id);
        contentValues.put(SyncToServerContract.TABLENAME, tableName);
        contentValues.put(SyncToServerContract.RECORDID, recordId);
        contentValues.put(SyncToServerContract.GROUPS, groupS);
        contentValues.put(SyncToServerContract.ACTION, action);
        contentValues.put(SyncToServerContract.PRIORITY, priority);
        contentValues.put(SyncToServerContract.STATUS, status);
        return contentValues;
    }

    public static SyncToServer fromCursor(Cursor cursor)
    {
        SyncToServer syncToServer = new SyncToServer();
        syncToServer.setId(cursor.getLong(cursor.getColumnIndex(SyncToServerContract._ID)));
        syncToServer.setTableName(cursor.getString(cursor.getColumnIndex(SyncToServerContract.TABLENAME)));
        syncToServer.setRecordId(cursor.getLong(cursor.getColumnIndex(SyncToServerContract.RECORDID)));
        syncToServer.setGroupS(cursor.getInt(cursor.getColumnIndex(SyncToServerContract.GROUPS)));
        syncToServer.setAction(cursor.getString(cursor.getColumnIndex(SyncToServerContract.ACTION)));
        syncToServer.setPriority(cursor.getInt(cursor.getColumnIndex(SyncToServerContract.PRIORITY)));
        syncToServer.setStatus(cursor.getString(cursor.getColumnIndex(SyncToServerContract.STATUS)));
        return syncToServer;
    }
}
