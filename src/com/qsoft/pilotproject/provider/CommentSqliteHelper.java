package com.qsoft.pilotproject.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * User: binhtv
 * Date: 10/23/13
 * Time: 2:05 PM
 */
public class CommentSqliteHelper extends SQLiteOpenHelper
{
    public static final String TABLE_COMMENT = "comment_list";
    public static final String COLUMN_ID = "comment_id";
    public static final String COLUMN_CONTENT = "comment_content";
    public static final String COLUMN_TITLE = "comment_title";
    public static final String COLUMN_DATECREATED = "comment_datecreated";
    private static final String DATABASE_NAME = "comment_list.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table " + TABLE_COMMENT + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null, " +
            COLUMN_DATECREATED + " text not null, " +
            COLUMN_CONTENT + " text not null );";

    public CommentSqliteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
        Log.d(CommentSqliteHelper.class.getName(), "upgrading database");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);
        onCreate(sqLiteDatabase);
    }
}
