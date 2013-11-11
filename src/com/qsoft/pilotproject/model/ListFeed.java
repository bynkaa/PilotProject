package com.qsoft.pilotproject.model;

import com.qsoft.pilotproject.model.dto.FeedDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 10:04 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListFeed
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
