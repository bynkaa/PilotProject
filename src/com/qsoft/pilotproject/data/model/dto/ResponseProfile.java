package com.qsoft.pilotproject.data.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 9:52 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseProfile {
    @JsonProperty("data")
    ProfileDTO profile;

    public ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }
}
