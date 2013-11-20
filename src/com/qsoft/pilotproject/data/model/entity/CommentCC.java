package com.qsoft.pilotproject.data.model.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.qsoft.pilotproject.data.provider.CCContract;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;

/**
 * User: Le
 * Date: 11/11/13
 */
@AdditionalAnnotation.Contract
@DatabaseTable(tableName = "comments")
@AdditionalAnnotation.DefaultContentUri(authority = CCContract.AUTHORITY, path = "comments")
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = CCContract.MIME_TYPE_VND, type = "comments")
public class CommentCC {
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    @AdditionalAnnotation.DefaultSortOrder
    @JsonIgnore
    private Long id;
    @DatabaseField
    @JsonProperty("id")
    private Long commentId;
    @DatabaseField
    @JsonProperty("sound_id")
    private Long soundId;
    @DatabaseField
    @JsonProperty("user_id")
    private Long userId;
    @DatabaseField
    @JsonProperty("comment")
    private String comment;
    @DatabaseField
    @JsonProperty("created_at")
    private String createdAt;
    @DatabaseField
    @JsonProperty("updated_at")
    private String updatedAt;
    @DatabaseField
    @JsonProperty("username")
    private String userName;
    @DatabaseField
    @JsonProperty("display_name")
    private String displayName;
    @DatabaseField
    @JsonProperty("avatar")
    private String avatar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getSoundId() {
        return soundId;
    }

    public void setSoundId(Long soundId) {
        this.soundId = soundId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(CommentCCContract._ID, id);
        values.put(CommentCCContract.COMMENTID, commentId);
        values.put(CommentCCContract.SOUNDID, soundId);
        values.put(CommentCCContract.USERID, userId);
        values.put(CommentCCContract.CREATEDAT, createdAt);
        values.put(CommentCCContract.UPDATEDAT, updatedAt);
        values.put(CommentCCContract.USERNAME, userName);
        values.put(CommentCCContract.DISPLAYNAME, displayName);
        values.put(CommentCCContract.AVATAR, avatar);
        values.put(CommentCCContract.COMMENT, comment);
        return values;
    }

    public static CommentCC fromCursor(Cursor cursor) {
        CommentCC comment = new CommentCC();
        comment.setId(cursor.getLong(cursor.getColumnIndex(CommentCCContract._ID)));
        comment.setCommentId(cursor.getLong(cursor.getColumnIndex(CommentCCContract.COMMENTID)));
        comment.setSoundId(cursor.getLong(cursor.getColumnIndex(CommentCCContract.SOUNDID)));
        comment.setComment(cursor.getString(cursor.getColumnIndex(CommentCCContract.COMMENT)));
        comment.setCreatedAt(cursor.getString(cursor.getColumnIndex(CommentCCContract.CREATEDAT)));
        comment.setUpdatedAt(cursor.getString(cursor.getColumnIndex(CommentCCContract.UPDATEDAT)));
        comment.setDisplayName(cursor.getString(cursor.getColumnIndex(CommentCCContract.DISPLAYNAME)));
        comment.setAvatar(cursor.getString(cursor.getColumnIndex(CommentCCContract.AVATAR)));
        comment.setUserName(cursor.getString(cursor.getColumnIndex(CommentCCContract.USERNAME)));
        comment.setUserId(cursor.getLong(cursor.getColumnIndex(CommentCCContract.USERID)));
        return comment;
    }
}

