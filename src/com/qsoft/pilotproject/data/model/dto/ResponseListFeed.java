package com.qsoft.pilotproject.data.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.pilotproject.data.model.entity.FeedCC;

import java.util.ArrayList;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 10:04 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseListFeed {
    @JsonProperty("data")
    private ArrayList<FeedCC> feedDTOs;

    public ArrayList<FeedCC> getFeedDTOs() {
        return feedDTOs;
    }

    public void setFeedDTOs(ArrayList<FeedCC> feedDTOs) {
        this.feedDTOs = feedDTOs;
    }
}
