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
    private static final int PROFILES = 300;
    private static final int PROFILES_ID = 301;
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

        matcher.addURI(authority, "profiles", PROFILES);
        matcher.addURI(authority, "profiles/*", PROFILES_ID);

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
                builder.table(OnlineDioContract.Feed.TABLE_NAME).where(OnlineDioContract.Feed._ID + "=?", id);
                Cursor cursor = builder.query(db, projection, sortOrder);
                Context context1 = getContext();
                assert context1 != null;
                cursor.setNotificationUri(context1.getContentResolver(), uri);
                return cursor;
            case FEEDS:
                builder.table(OnlineDioContract.Feed.TABLE_NAME).where(selection, selectionArgs);
                Cursor cursors = builder.query(db, projection, sortOrder);
                Context context = getContext();
                assert context != null;
                cursors.setNotificationUri(context.getContentResolver(), uri);
                return cursors;
            case PROFILES:
                builder.table(OnlineDioContract.Profile.TABLE_NAME).where(selection, selectionArgs);
                Cursor cursor3 = builder.query(db, projection, sortOrder);
                Context context3 = getContext();
                assert context3 != null;
                cursor3.setNotificationUri(context3.getContentResolver(), uri);
                return cursor3;
            case PROFILES_ID:
                String idProfile = uri.getLastPathSegment();
                builder.table(OnlineDioContract.Profile.TABLE_NAME).where(OnlineDioContract.Profile._ID + "=?", idProfile);
                Cursor cursor4 = builder.query(db, projection, sortOrder);
                Context context4 = getContext();
                assert context4 != null;
                cursor4.setNotificationUri(context4.getContentResolver(), uri);
                return cursor4;
            case COMMENTS:
                builder.table(OnlineDioContract.Comment.TABLE_NAME).where(selection, selectionArgs);
                Cursor cursor5 = builder.query(db, projection, sortOrder);
                Context context5 = getContext();
                assert context5 != null;
                cursor5.setNotificationUri(context5.getContentResolver(), uri);
                return cursor5;
            case COMMENTS_ID:
                String idComment = uri.getLastPathSegment();
                builder.table(OnlineDioContract.Comment.TABLE_NAME).where(OnlineDioContract.Comment._ID + "=?", idComment);
                Cursor cursor6 = builder.query(db, projection, sortOrder);
                Context context6 = getContext();
                assert context6 != null;
                cursor6.setNotificationUri(context6.getContentResolver(), uri);
                return cursor6;
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
            case PROFILES:
                return OnlineDioContract.Profile.CONTENT_TYPE;
            case PROFILES_ID:
                return OnlineDioContract.Profile.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        final SQLiteDatabase db = onlineDioHelper.getWritableDatabase();
        assert db != null;
        final int match = uriMatcher.match(uri);
        Uri result;
        switch (match)
        {
            case FEEDS:
                long id = db.insertOrThrow(OnlineDioContract.Feed.TABLE_NAME, null, contentValues);
                result = Uri.parse(OnlineDioContract.Feed.CONTENT_URI + "/" + id);
                break;
            case FEEDS_ID:
                throw new UnsupportedOperationException("Insert not support: " + uri);
            case PROFILES:
                long idProfile = db.insertOrThrow(OnlineDioContract.Profile.TABLE_NAME, null, contentValues);
                result = Uri.parse(OnlineDioContract.Profile.CONTENT_URI + "/" + idProfile);
                break;
            case COMMENTS:
                long idComment = db.insertOrThrow(OnlineDioContract.Comment.TABLE_NAME, null, contentValues);
                result = Uri.parse(OnlineDioContract.Comment.CONTENT_URI + "/" + idComment);
                break;
            case COMMENTS_ID:
                throw new UnsupportedOperationException("Insert not support: " + uri);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        Context context = getContext();
        assert context != null;
        context.getContentResolver().notifyChange(uri, null, false);
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = onlineDioHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int count;
        switch (match)
        {
            case FEEDS:
                count = builder.table(OnlineDioContract.Feed.TABLE_NAME).where(selection, selectionArgs).delete(db);
                break;
            case FEEDS_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(OnlineDioContract.Feed.TABLE_NAME)
                        .where(OnlineDioContract.Feed._ID + "=?", id)
                        .where(selection, selectionArgs).delete(db);
                break;
            case PROFILES:
                count = builder.table(OnlineDioContract.Profile.TABLE_NAME).where(selection, selectionArgs).delete(db);
                break;
            case PROFILES_ID:
                String id1 = uri.getLastPathSegment();
                count = builder.table(OnlineDioContract.Profile.TABLE_NAME)
                        .where(OnlineDioContract.Profile._ID + "=?", id1)
                        .where(selection, selectionArgs).delete(db);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Context context = getContext();
        assert context != null;
        context.getContentResolver().notifyChange(uri, null, false);
        return count;

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
    {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = onlineDioHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int count;
        switch (match)
        {
            case FEEDS:
                count = builder.table(OnlineDioContract.Feed.TABLE_NAME)
                        .where(selection, selectionArgs).update(db, contentValues);
                break;
            case FEEDS_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(OnlineDioContract.Feed.TABLE_NAME)
                        .where(OnlineDioContract.Feed._ID + "=?", id)
                        .where(selection, selectionArgs)
                        .update(db, contentValues);
                break;
            case PROFILES:
                count = builder.table(OnlineDioContract.Profile.TABLE_NAME)
                        .where(selection, selectionArgs).update(db, contentValues);
                break;
            case PROFILES_ID:
                String id1 = uri.getLastPathSegment();
                count = builder.table(OnlineDioContract.Profile.TABLE_NAME)
                        .where(OnlineDioContract.Profile._ID + "=?", id1)
                        .where(selection, selectionArgs)
                        .update(db, contentValues);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Context context = getContext();
        assert context != null;
        context.getContentResolver().notifyChange(uri, null, false);
        return count;
    }
}
