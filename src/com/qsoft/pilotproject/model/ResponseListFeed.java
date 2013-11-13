package com.qsoft.pilotproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.pilotproject.model.dto.FeedDTO;

import java.util.ArrayList;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 10:04 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseListFeed
{
    @JsonProperty("data")
    private ArrayList<FeedDTO> feedDTOs;

    public ArrayList<FeedDTO> getFeedDTOs()
    {
        return feedDTOs;
    }

    public void setFeedDTOs(ArrayList<FeedDTO> feedDTOs)
    {
        this.feedDTOs = feedDTOs;
    }
}
