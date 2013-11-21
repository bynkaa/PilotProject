package com.qsoft.pilotproject.data.model.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.qsoft.pilotproject.data.provider.CCContract;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;

/**
 * User: binhtv
 * Date: 11/15/13
 * Time: 8:50 AM
 */
@AdditionalAnnotation.Contract
@DatabaseTable(tableName = "feeds")
@AdditionalAnnotation.DefaultContentUri(authority = CCContract.AUTHORITY, path = "feeds")
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = CCContract.MIME_TYPE_VND, type = "feeds")
public class FeedCC
{
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    @AdditionalAnnotation.DefaultSortOrder
    private Long _id;
    @DatabaseField
    @SerializedName("id")
    private long feedId;
    @DatabaseField
    @SerializedName("user_id")
    private long userId;
    @DatabaseField
    @SerializedName("title")
    private String title;
    @DatabaseField
    @SerializedName("thumbnail")
    private String thumbnail;
    @DatabaseField
    @SerializedName("description")
    private String description;
    @DatabaseField
    @SerializedName("sound_path")
    private String soundPath;
    @DatabaseField
    @SerializedName("duration")
    private String duration;
    @DatabaseField
    @SerializedName("played")
    private String played;
    @DatabaseField
    @SerializedName("created_at")
    private String createdAt;
    @DatabaseField
    @SerializedName("updated_at")
    private String updatedAt;
    @DatabaseField
    @SerializedName("likes")
    private int likes;
    @DatabaseField
    @SerializedName("viewed")
    private int viewed;
    @DatabaseField
    @SerializedName("comments")
    private int comments;
    @DatabaseField
    @SerializedName("username")
    private String userName;
    @DatabaseField
    @SerializedName("display_name")
    private String displayName;
    @DatabaseField
    @SerializedName("avatar")
    private String avatar;

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public long getFeedId()
    {
        return feedId;
    }

    public void setFeedId(long feedId)
    {
        this.feedId = feedId;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getSoundPath()
    {
        return soundPath;
    }

    public void setSoundPath(String soundPath)
    {
        this.soundPath = soundPath;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getPlayed()
    {
        return played;
    }

    public void setPlayed(String played)
    {
        this.played = played;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    public int getViewed()
    {
        return viewed;
    }

    public void setViewed(int viewed)
    {
        this.viewed = viewed;
    }

    public int getComments()
    {
        return comments;
    }

    public void setComments(int comments)
    {
        this.comments = comments;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(FeedCCContract._ID, _id);
        values.put(FeedCCContract.FEEDID, feedId);
        values.put(FeedCCContract.USERID, userId);
        values.put(FeedCCContract.TITLE, title);
        values.put(FeedCCContract.THUMBNAIL, thumbnail);
        values.put(FeedCCContract.DESCRIPTION, description);
        values.put(FeedCCContract.SOUNDPATH, soundPath);
        values.put(FeedCCContract.DURATION, duration);
        values.put(FeedCCContract.PLAYED, played);
        values.put(FeedCCContract.CREATEDAT, createdAt);
        values.put(FeedCCContract.UPDATEDAT, updatedAt);
        values.put(FeedCCContract.AVATAR, avatar);
        values.put(FeedCCContract.COMMENTS, comments);
        values.put(FeedCCContract.DISPLAYNAME, displayName);
        values.put(FeedCCContract.LIKES, likes);
        values.put(FeedCCContract.USERNAME, userName);
        values.put(FeedCCContract.VIEWED, viewed);
        return values;
    }

    public static FeedCC fromCursor(Cursor cursorFeed)
    {
        FeedCC feed = new FeedCC();
        feed.set_id(cursorFeed.getLong(cursorFeed.getColumnIndex(FeedCCContract._ID)));
        feed.setFeedId(cursorFeed.getLong(cursorFeed.getColumnIndex(FeedCCContract.FEEDID)));
        feed.setUserId(cursorFeed.getLong(cursorFeed.getColumnIndex(FeedCCContract.USERID)));
        feed.setTitle(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.TITLE)));
        feed.setThumbnail(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.THUMBNAIL)));
        feed.setDescription(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.DESCRIPTION)));
        feed.setSoundPath(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.SOUNDPATH)));
        feed.setDuration(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.DURATION)));
        feed.setPlayed(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.PLAYED)));
        feed.setCreatedAt(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.CREATEDAT)));
        feed.setUpdatedAt(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.UPDATEDAT)));
        feed.setLikes(cursorFeed.getInt(cursorFeed.getColumnIndex(FeedCCContract.LIKES)));
        feed.setViewed(cursorFeed.getInt(cursorFeed.getColumnIndex(FeedCCContract.VIEWED)));
        feed.setUserName(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.USERNAME)));
        feed.setDisplayName(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.DISPLAYNAME)));
        feed.setAvatar(cursorFeed.getString(cursorFeed.getColumnIndex(FeedCCContract.AVATAR)));
        feed.setComments(cursorFeed.getInt(cursorFeed.getColumnIndex(FeedCCContract.COMMENTS)));
        return feed;
    }

}
