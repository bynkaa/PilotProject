package com.qsoft.pilotproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.pilotproject.model.cc.ProfileCC;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 9:52 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseProfile
{
    @JsonProperty("data")
    ProfileCC profile;

    public ProfileCC getProfile()
    {
        return profile;
    }

    public void setProfile(ProfileCC profile)
    {
        this.profile = profile;
    }
}
