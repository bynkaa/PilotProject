package com.qsoft.pilotproject.data.model.dto;

import com.google.gson.annotations.SerializedName;
import com.qsoft.pilotproject.data.model.entity.CommentCC;

import java.util.List;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 10:06 AM
 */
public class ResponseComment
{
    @SerializedName("data")
    private ResponseCommentList commentData;

    public ResponseCommentList getCommentData()
    {
        return commentData;
    }

    public void setCommentData(ResponseCommentList commentData)
    {
        this.commentData = commentData;
    }

    public List<CommentCC> getComments()
    {
        return commentData.getCommentDTOs();
    }
}
