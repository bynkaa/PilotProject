package com.qsoft.pilotproject.model;

import android.provider.BaseColumns;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;

/**
 * User: Le
 * Date: 11/11/13
 */
@AdditionalAnnotation.Contract()
@DatabaseTable(tableName = "comments")
@AdditionalAnnotation.DefaultContentUri(authority = "com.qsoft.pilotproject", path = "comments")
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = "com.qsoft.pilotproject.provider", type = "comments")
public class CommentCC
{
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    @AdditionalAnnotation.DefaultSortOrder
    private Long commentId;
    @DatabaseField
    private Long soundId;
    @DatabaseField
    private Long userId;
    @DatabaseField
    private String comment;
    @DatabaseField
    private String createdAt;
    @DatabaseField
    private String updatedAt;
    @DatabaseField
    private String userName;
    @DatabaseField
    private String displayName;
    @DatabaseField
    private String avatar;
}
