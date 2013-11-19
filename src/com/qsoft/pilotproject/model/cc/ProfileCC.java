package com.qsoft.pilotproject.model.cc;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.qsoft.pilotproject.provider.cc.CCContract;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;

/**
 * User: binhtv
 * Date: 11/18/13
 * Time: 9:00 AM
 */
@AdditionalAnnotation.Contract
@DatabaseTable(tableName = "profiles")
@AdditionalAnnotation.DefaultContentUri(authority = CCContract.AUTHORITY, path = "profiles")
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = CCContract.MIME_TYPE_VND, type = "profiles")
public class ProfileCC
{
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    @AdditionalAnnotation.DefaultSortOrder
    @JsonIgnore
    private Long id;
    @DatabaseField
    @JsonProperty("id")
    private Long userId;
    @DatabaseField
    @JsonProperty("facebook_id")
    private Long facebookId;
    @DatabaseField
    @JsonProperty("username")
    private String userName;
    @DatabaseField
    @JsonProperty("password")
    private String password;
    @DatabaseField
    @JsonProperty("avatar")
    private String avatar;
    @DatabaseField
    @JsonProperty("cover_image")
    private String coverImage;
    @DatabaseField
    @JsonProperty("display_name")
    private String displayName;
    @DatabaseField
    @JsonProperty("full_name")
    private String fullName;
    @DatabaseField
    @JsonProperty("phone")
    private String phone;
    @DatabaseField
    @JsonProperty("birthday")
    private String birthday;
    @DatabaseField
    @JsonProperty("gender")
    private int gender;
    @DatabaseField
    @JsonProperty("country_id")
    private String countryId;
    @DatabaseField
    @JsonProperty("storage_plan_id")
    private int storagePlanId;
    @DatabaseField
    @JsonProperty("description")
    private String description;
    @DatabaseField
    @JsonProperty("created_at")
    private String createdAt;
    @DatabaseField
    @JsonProperty("updated_at")
    private String updatedAt;
    @DatabaseField
    @JsonProperty("sounds")
    private int sound;
    @DatabaseField
    @JsonProperty("favorites")
    private int favorite;
    @DatabaseField
    @JsonProperty("likes")
    private int like;
    @DatabaseField
    @JsonProperty("followings")
    private int following;
    @DatabaseField
    @JsonProperty("audiences")
    private int audience;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getFacebookId()
    {
        return facebookId;
    }

    public void setFacebookId(Long facebookId)
    {
        this.facebookId = facebookId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public int getStoragePlanId()
    {
        return storagePlanId;
    }

    public void setStoragePlanId(int storagePlanId)
    {
        this.storagePlanId = storagePlanId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public int getSound()
    {
        return sound;
    }

    public void setSound(int sound)
    {
        this.sound = sound;
    }

    public int getFavorite()
    {
        return favorite;
    }

    public void setFavorite(int favorite)
    {
        this.favorite = favorite;
    }

    public int getLike()
    {
        return like;
    }

    public void setLike(int like)
    {
        this.like = like;
    }

    public int getFollowing()
    {
        return following;
    }

    public void setFollowing(int following)
    {
        this.following = following;
    }

    public int getAudience()
    {
        return audience;
    }

    public void setAudience(int audience)
    {
        this.audience = audience;
    }

    @JsonIgnore
    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(ProfileCCContract._ID, id);
        values.put(ProfileCCContract.USERID, userId);
        values.put(ProfileCCContract.FACEBOOKID, facebookId);
        values.put(ProfileCCContract.USERNAME, userName);
        values.put(ProfileCCContract.PASSWORD, password);
        values.put(ProfileCCContract.AVATAR, avatar);
        values.put(ProfileCCContract.COVERIMAGE, coverImage);
        values.put(ProfileCCContract.DISPLAYNAME, displayName);
        values.put(ProfileCCContract.FULLNAME, fullName);
        values.put(ProfileCCContract.PHONE, phone);
        values.put(ProfileCCContract.BIRTHDAY, birthday);
        values.put(ProfileCCContract.GENDER, gender);
        values.put(ProfileCCContract.COUNTRYID, countryId);
        values.put(ProfileCCContract.STORAGEPLANID, storagePlanId);
        values.put(ProfileCCContract.DESCRIPTION, description);
        values.put(ProfileCCContract.CREATEDAT, createdAt);
        values.put(ProfileCCContract.UPDATEDAT, updatedAt);
        values.put(ProfileCCContract.SOUND, sound);
        values.put(ProfileCCContract.FAVORITE, favorite);
        values.put(ProfileCCContract.LIKE, like);
        values.put(ProfileCCContract.FOLLOWING, following);
        values.put(ProfileCCContract.AUDIENCE, audience);
        return values;
    }

    @JsonIgnore
    public static ProfileCC fromCursor(Cursor cursor)
    {
        ProfileCC profile = new ProfileCC();
        profile.setId(cursor.getLong(cursor.getColumnIndex(ProfileCCContract._ID)));
        profile.setUserId(cursor.getLong(cursor.getColumnIndex(ProfileCCContract.USERID)));
        profile.setFacebookId(cursor.getLong(cursor.getColumnIndex(ProfileCCContract.FACEBOOKID)));
        profile.setUserName(cursor.getString(cursor.getColumnIndex(ProfileCCContract.USERNAME)));
        profile.setPassword(cursor.getString(cursor.getColumnIndex(ProfileCCContract.PASSWORD)));
        profile.setAvatar(cursor.getString(cursor.getColumnIndex(ProfileCCContract.AVATAR)));
        profile.setCoverImage(cursor.getString(cursor.getColumnIndex(ProfileCCContract.COVERIMAGE)));
        profile.setDisplayName(cursor.getString(cursor.getColumnIndex(ProfileCCContract.DISPLAYNAME)));
        profile.setFullName(cursor.getString(cursor.getColumnIndex(ProfileCCContract.FULLNAME)));
        profile.setPhone(cursor.getString(cursor.getColumnIndex(ProfileCCContract.PHONE)));
        profile.setBirthday(cursor.getString(cursor.getColumnIndex(ProfileCCContract.BIRTHDAY)));
        profile.setGender(cursor.getInt(cursor.getColumnIndex(ProfileCCContract.GENDER)));
        profile.setCountryId(cursor.getString(cursor.getColumnIndex(ProfileCCContract.COUNTRYID)));
        profile.setStoragePlanId(cursor.getInt(cursor.getColumnIndex(ProfileCCContract.STORAGEPLANID)));
        profile.setDescription(cursor.getString(cursor.getColumnIndex(ProfileCCContract.DESCRIPTION)));
        profile.setCreatedAt(cursor.getString(cursor.getColumnIndex(ProfileCCContract.CREATEDAT)));
        profile.setUpdatedAt(cursor.getString(cursor.getColumnIndex(ProfileCCContract.UPDATEDAT)));
        profile.setSound(cursor.getInt(cursor.getColumnIndex(ProfileCCContract.SOUND)));
        profile.setFavorite(cursor.getInt(cursor.getColumnIndex(ProfileCCContract.FAVORITE)));
        profile.setLike(cursor.getInt(cursor.getColumnIndex(ProfileCCContract.LIKE)));
        profile.setFollowing(cursor.getInt(cursor.getColumnIndex(ProfileCCContract.FOLLOWING)));
        profile.setAudience(cursor.getInt(cursor.getColumnIndex(ProfileCCContract.AUDIENCE)));
        return profile;
    }
}
