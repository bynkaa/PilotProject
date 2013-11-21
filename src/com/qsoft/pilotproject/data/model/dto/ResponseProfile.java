package com.qsoft.pilotproject.data.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 9:52 AM
 */
public class ResponseProfile
{
    @SerializedName("data")
    ProfileDTO profile;

    public ProfileDTO getProfile()
    {
        return profile;
    }

    public void setProfile(ProfileDTO profile)
    {
        this.profile = profile;
    }
}
