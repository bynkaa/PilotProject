package com.qsoft.pilotproject.model.dto;

import android.content.ContentValues;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.pilotproject.provider.OnlineDioContract;

import java.io.Serializable;

/**
 * User: BinkaA
 * Date: 11/4/13
 * Time: 10:32 PM
 */
public class ProfileDTO implements Serializable
{
    @JsonProperty("id")
    private Long userId;
    @JsonProperty("facebook_id")
    private Long facebookId;
    @JsonProperty("usename")
    private String userName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("cover_image")
    private String coverImage;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("gender")
    private int gender;
    @JsonProperty("country_id")
    private String countryId;
    @JsonProperty("storage_plan_id")
    private int storagePlanId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("sounds")
    private int sound;
    @JsonProperty("favorites")
    private int favorite;
    @JsonProperty("likes")
    private int like;
    @JsonProperty("followings")
    private int following;
    @JsonProperty("audiences")
    private int audience;

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

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(OnlineDioContract.Profile.COLUMN_USER_ID, userId);
        values.put(OnlineDioContract.Profile.COLUMN_FACEBOOK_ID, facebookId);
        values.put(OnlineDioContract.Profile.COLUMN_USERNAME, userName);
        values.put(OnlineDioContract.Profile.COLUMN_PASSWORD, password);
        values.put(OnlineDioContract.Profile.COLUMN_AVATAR, avatar);
        values.put(OnlineDioContract.Profile.COLUMN_COVER_IMAGE, coverImage);
        values.put(OnlineDioContract.Profile.COLUMN_DISPLAY_NAME, displayName);
        values.put(OnlineDioContract.Profile.COLUMN_FULL_NAME, fullName);
        values.put(OnlineDioContract.Profile.COLUMN_PHONE, phone);
        values.put(OnlineDioContract.Profile.COLUMN_BIRTHDAY, birthday);
        values.put(OnlineDioContract.Profile.COLUMN_GENDER, gender);
        values.put(OnlineDioContract.Profile.COLUMN_COUNTRY_ID, countryId);
        values.put(OnlineDioContract.Profile.COLUMN_STORAGE_PLAN_ID, storagePlanId);
        values.put(OnlineDioContract.Profile.COLUMN_DESCRIPTION, description);
        values.put(OnlineDioContract.Profile.COLUMN_CREATED_AT, createdAt);
        values.put(OnlineDioContract.Profile.COLUMN_UPDATED_AT, updatedAt);
        values.put(OnlineDioContract.Profile.COLUMN_SOUNDS, sound);
        values.put(OnlineDioContract.Profile.COLUMN_FAVORITES, favorite);
        values.put(OnlineDioContract.Profile.COLUMN_LIKES, like);
        values.put(OnlineDioContract.Profile.COLUMN_FOLLOWING, following);
        values.put(OnlineDioContract.Profile.COLUMN_AUDIENCE, audience);
        return values;
    }
}
