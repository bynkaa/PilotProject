package com.qsoft.pilotproject.model;

import android.database.Cursor;
import com.qsoft.pilotproject.model.dto.ProfileDTO;
import com.qsoft.pilotproject.provider.OnlineDioContract;

/**
 * User: BinkaA
 * Date: 11/4/13
 * Time: 10:54 PM
 */
public class Profile extends ProfileDTO
{
    private long id;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public static Profile fromCursor(Cursor cursor)
    {
        Profile profile = new Profile();
        profile.setId(cursor.getLong(cursor.getColumnIndex(OnlineDioContract.Profile._ID)));
        profile.setUserId(cursor.getLong(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_USER_ID)));
        profile.setFacebookId(cursor.getLong(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_FACEBOOK_ID)));
        profile.setUserName(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_USERNAME)));
        profile.setPassword(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_PASSWORD)));
        profile.setAvatar(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_AVATAR)));
        profile.setCoverImage(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_COVER_IMAGE)));
        profile.setDisplayName(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_DISPLAY_NAME)));
        profile.setFullName(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_FULL_NAME)));
        profile.setPhone(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_PHONE)));
        profile.setBirthday(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_BIRTHDAY)));
        profile.setGender(cursor.getInt(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_GENDER)));
        profile.setCountryId(cursor.getInt(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_COUNTRY_ID)));
        profile.setStoragePlanId(cursor.getInt(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_STORAGE_PLAN_ID)));
        profile.setDescription(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_DESCRIPTION)));
        profile.setCreatedAt(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_CREATED_AT)));
        profile.setUpdatedAt(cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_UPDATED_AT)));
        profile.setSound(cursor.getInt(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_SOUNDS)));
        profile.setFavorite(cursor.getInt(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_FAVORITES)));
        profile.setLike(cursor.getInt(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_LIKES)));
        profile.setFollowing(cursor.getInt(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_FOLLOWING)));
        profile.setAudience(cursor.getInt(cursor.getColumnIndex(OnlineDioContract.Profile.COLUMN_AUDIENCE)));
        return profile;
    }
}
