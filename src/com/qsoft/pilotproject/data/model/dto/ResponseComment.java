package com.qsoft.pilotproject.data.model.dto;

import com.google.gson.annotations.SerializedName;
import com.qsoft.pilotproject.data.model.entity.CommentCC;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 10:06 AM
 */
public class ResponseComment
{
    @SerializedName("data")
    private CommentData commentData;

    public CommentData getCommentData()
    {
        return commentData;
    }

    public void setCommentData(CommentData commentData)
    {
        this.commentData = commentData;
    }

    public List<CommentCC> getComments()
    {
        return commentData.getCommentDTOs();
    }
}

class CommentData
{
    @SerializedName("total_comments")
    private int totalComment;
    @SerializedName("comments")
    private ArrayList<CommentCC> commentDTOs;

    int getTotalComment()
    {
        return totalComment;
    }

    void setTotalComment(int totalComment)
    {
        this.totalComment = totalComment;
    }

    ArrayList<CommentCC> getCommentDTOs()
    {
        return commentDTOs;
    }

    void setCommentDTOs(ArrayList<CommentCC> commentDTOs)
    {
        this.commentDTOs = commentDTOs;
    }
}

