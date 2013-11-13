package com.qsoft.pilotproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.pilotproject.model.dto.ProfileDTO;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 9:52 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseProfile
{
    @JsonProperty("data")
    ProfileDTO profileDTO;

    public ProfileDTO getProfileDTO()
    {
        return profileDTO;
    }

    public void setProfileDTO(ProfileDTO profileDTO)
    {
        this.profileDTO = profileDTO;
    }
}
