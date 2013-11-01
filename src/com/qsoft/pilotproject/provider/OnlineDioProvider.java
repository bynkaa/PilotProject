package com.qsoft.pilotproject.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.qsoft.pilotproject.utils.SelectionBuilder;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 11:39 AM
 */
public class OnlineDioProvider extends ContentProvider
{
    private static final String TAG = "OnlineDioProvider";
    private static final int FEEDS = 100;
    private static final int FEEDS_ID = 101;
    private static final int COMMENTS = 200;
    private static final int COMMENTS_ID = 201;
    OnlineDioDatabase onlineDioHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = OnlineDioContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "feeds", FEEDS);
        matcher.addURI(authority, "feeds/*", FEEDS_ID);

        matcher.addURI(authority, "comments", COMMENTS);
        matcher.addURI(authority, "comments/*", COMMENTS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate()
    {
        onlineDioHelper = new OnlineDioDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        Log.d(TAG, "query(uri)");
        final SQLiteDatabase db = onlineDioHelper.getReadableDatabase();
        SelectionBuilder builder = new SelectionBuilder();
        int uriMatch = uriMatcher.match(uri);
        switch (uriMatch)
        {
            case FEEDS_ID:
                String id = uri.getLastPathSegment();
                builder.where(OnlineDioContract.Feed._ID + "=?" + id);
            case FEEDS:
                builder.table(OnlineDioContract.Feed.TABLE_NAME).where(selection, selectionArgs);
                Cursor cursor = builder.query(db, projection, sortOrder);
                Context context = getContext();
                assert context != null;
                cursor.setNotificationUri(context.getContentResolver(), uri);
                return cursor;


        }
        return null;
    }

    @Override
    public String getType(Uri uri)
    {
        final int match = uriMatcher.match(uri);
        switch (match)
        {
            case FEEDS:
                return OnlineDioContract.Feed.CONTENT_TYPE;
            case FEEDS_ID:
                return OnlineDioContract.Feed.CONTENT_ITEM_TYPE;
            case COMMENTS:
                return OnlineDioContract.Comment.CONTENT_TYPE;
            case COMMENTS_ID:
                return OnlineDioContract.Comment.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int delete(Uri uri, String s, String[] strings)
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
