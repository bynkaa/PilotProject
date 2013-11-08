package com.qsoft.pilotproject.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.qsoft.pilotproject.model.dto.CommentDTO;
import com.qsoft.pilotproject.provider.OnlineDioContract;

/**
 * User: BinkaA
 * Date: 10/18/13
 * Time: 1:58 AM
 */
public class Comment extends CommentDTO
{
    private Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = super.getContentValues();
        values.put(OnlineDioContract.Comment._ID, id);
        return values;
    }

    public static Comment fromCursor(Cursor cursor)
    {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(cursor.getColumnIndex(OnlineDioContract.Comment._ID)));
        comment.setCommentId(cursor.getLong(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_ID)));
        comment.setSoundId(cursor.getLong(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_SOUND_ID)));
        comment.setComment(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_CONTENT)));
        comment.setCreatedAt(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_CREATED_AT)));
        comment.setUpdatedAt(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_UPDATED_AT)));
        comment.setDisplayName(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_DISPLAY_NAME)));
        comment.setAvatar(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_AVATAR)));
        comment.setUserName(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_USER_NAME)));
        comment.setUserId(cursor.getLong(cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_USER_ID)));
        return comment;
    }
}
