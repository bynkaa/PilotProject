package com.qsoft.pilotproject.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.qsoft.pilotproject.model.dto.FeedDTO;
import com.qsoft.pilotproject.provider.OnlineDioContract;

/**
 * User: binhtv
 * Date: 10/15/13
 * Time: 9:05 AM
 */
public class Feed extends FeedDTO
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

    @Override
    public ContentValues getContentValues()
    {
        ContentValues contentValues = super.getContentValues();
        contentValues.put(OnlineDioContract.Feed._ID, id);
        return contentValues;
    }

    public static Feed fromCursor(Cursor cursorFeed)
    {
        Feed feed = new Feed();
        feed.setId(cursorFeed.getLong(cursorFeed.getColumnIndex(OnlineDioContract.Feed._ID)));
        feed.setFeedId(cursorFeed.getLong(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_ID)));
        feed.setUserId(cursorFeed.getLong(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_USER_ID)));
        feed.setTitle(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_TITLE)));
        feed.setThumbnail(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_THUMBNAIL)));
        feed.setDescription(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_DESCRIPTION)));
        feed.setSoundPath(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_SOUND_PATH)));
        feed.setDuration(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_DURATION)));
        feed.setPlayed(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_PLAYED)));
        feed.setCreatedAt(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_CREATED_AT)));
        feed.setUpdatedAt(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_UPDATED_AT)));
        feed.setLikes(cursorFeed.getInt(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_LIKES)));
        feed.setViewed(cursorFeed.getInt(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_VIEWED)));
        feed.setUserName(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_USER_NAME)));
        feed.setDisplayName(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_DISPLAY_NAME)));
        feed.setAvatar(cursorFeed.getString(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_AVATAR)));
        feed.setComments(cursorFeed.getInt(cursorFeed.getColumnIndex(OnlineDioContract.Feed.COLUMN_COMMENTS)));
        return feed;
    }
    //    private String title;
//    private String composer;
//    private int likeNumber;
//    private int commentNumber;
//    private String updateStatus;
//
//    public String getTitle()
//    {
//        return title;
//    }
//
//    public void setTitle(String title)
//    {
//        this.title = title;
//    }
//
//    public String getComposer()
//    {
//        return composer;
//    }
//
//    public void setComposer(String composer)
//    {
//        this.composer = composer;
//    }
//
//    public int getLikeNumber()
//    {
//        return likeNumber;
//    }
//
//    public void setLikeNumber(int likeNumber)
//    {
//        this.likeNumber = likeNumber;
//    }
//
//    public int getCommentNumber()
//    {
//        return commentNumber;
//    }
//
//    public void setCommentNumber(int commentNumber)
//    {
//        this.commentNumber = commentNumber;
//    }
//
//    public String getUpdateStatus()
//    {
//        return updateStatus;
//    }
//
//    public void setUpdateStatus(String updateStatus)
//    {
//        this.updateStatus = updateStatus;
//    }
}
