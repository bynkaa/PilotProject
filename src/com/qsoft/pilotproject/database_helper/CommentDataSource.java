package com.qsoft.pilotproject.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.qsoft.pilotproject.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/23/13
 * Time: 2:12 PM
 */
public class CommentDataSource
{
    private SQLiteDatabase sqLiteDatabase;
    private CommentSqliteHelper commentSqliteHelper;
    private String[] allColumns = {CommentSqliteHelper.COLUMN_ID,CommentSqliteHelper.COLUMN_TITLE, CommentSqliteHelper.COLUMN_CONTENT, CommentSqliteHelper.COLUMN_DATECREATED};

    public CommentDataSource(Context context)
    {
        commentSqliteHelper = new CommentSqliteHelper(context);
    }

    public void open()
    {
        sqLiteDatabase = commentSqliteHelper.getWritableDatabase();
    }
    public void close()
    {
        commentSqliteHelper.close();
    }
    public long createComment(Comment comment)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CommentSqliteHelper.COLUMN_TITLE,comment.getTitle());
        contentValues.put(CommentSqliteHelper.COLUMN_DATECREATED,comment.getTimeCreated());
        contentValues.put(CommentSqliteHelper.COLUMN_CONTENT,comment.getContent());
        return sqLiteDatabase.insert(CommentSqliteHelper.TABLE_COMMENT,null,contentValues);

    }
    public List<Comment> getAllComment()
    {
        List<Comment> comments = new ArrayList<Comment>();
        Cursor cursor = sqLiteDatabase.query(CommentSqliteHelper.TABLE_COMMENT,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
        }
        return comments;
    }

    public Comment cursorToComment(Cursor cursor)
    {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setTitle(cursor.getString(1));
        comment.setContent(cursor.getString(2));
        comment.setTimeCreated(cursor.getString(3));
        return comment;
    }


}
