package com.qsoft.pilotproject.data.model.dto;

import com.google.gson.annotations.SerializedName;
import com.qsoft.pilotproject.data.model.entity.CommentCC;

import java.util.ArrayList;

/**
 * User: binhtv
 * Date: 11/22/13
 * Time: 10:40 AM
 */
public class ResponseCommentList
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
