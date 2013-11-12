package com.qsoft.pilotproject.model;

import com.qsoft.pilotproject.model.dto.ProfileDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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
