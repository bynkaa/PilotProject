package com.qsoft.pilotproject.data.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * User: binhtv
 * Date: 11/19/13
 * Time: 3:17 PM
 */
public class ProfileDTO
{
    @SerializedName("id")
    private Long userId;
    @SerializedName("facebook_id")
    private Long facebookId;
    @SerializedName("username")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("cover_image")
    private String coverImage;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("phone")
    private String phone;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("gender")
    private int gender;
    @SerializedName("country_id")
    private String countryId;
    @SerializedName("storage_plan_id")
    private int storagePlanId;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("sounds")
    private int sound;
    @SerializedName("favorites")
    private int favorite;
    @SerializedName("likes")
    private int like;
    @SerializedName("followings")
    private int following;
    @SerializedName("audiences")
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
}
