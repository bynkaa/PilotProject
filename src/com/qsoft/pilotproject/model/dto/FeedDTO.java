package com.qsoft.pilotproject.model.dto;

import android.content.ContentValues;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 8:48 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedDTO
{
    @JsonProperty(("id"))
    private long feedId;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("description")
    private String description;
    @JsonProperty("sound_path")
    private String soundPath;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("played")
    private String played;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("likes")
    private int likes;
    @JsonProperty("viewed")
    private int viewed;
    @JsonProperty("comments")
    private int comments;
    @JsonProperty("username")
    private String userName;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("avatar")
    private String avatar;

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
        values.put(OnlineDioContract.Feed.COLUMN_ID, feedId);
        values.put(OnlineDioContract.Feed.COLUMN_USER_ID, userId);
        values.put(OnlineDioContract.Feed.COLUMN_TITLE, title);
        values.put(OnlineDioContract.Feed.COLUMN_THUMBNAIL, thumbnail);
        values.put(OnlineDioContract.Feed.COLUMN_DESCRIPTION, description);
        values.put(OnlineDioContract.Feed.COLUMN_SOUND_PATH, soundPath);
        values.put(OnlineDioContract.Feed.COLUMN_DURATION, duration);
        values.put(OnlineDioContract.Feed.COLUMN_PLAYED, played);
        values.put(OnlineDioContract.Feed.COLUMN_CREATED_AT, createdAt);
        values.put(OnlineDioContract.Feed.COLUMN_UPDATED_AT, updatedAt);
        values.put(OnlineDioContract.Feed.COLUMN_AVATAR, avatar);
        values.put(OnlineDioContract.Feed.COLUMN_COMMENTS, comments);
        values.put(OnlineDioContract.Feed.COLUMN_DISPLAY_NAME, displayName);
        values.put(OnlineDioContract.Feed.COLUMN_LIKES, likes);
        values.put(OnlineDioContract.Feed.COLUMN_USER_NAME, userName);
        values.put(OnlineDioContract.Feed.COLUMN_VIEWED, viewed);
        return values;
    }
}
