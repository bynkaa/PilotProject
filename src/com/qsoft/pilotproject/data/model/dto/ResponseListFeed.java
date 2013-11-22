package com.qsoft.pilotproject.data.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 10:04 AM
 */
public class ResponseListFeed
{
    @SerializedName("data")
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
