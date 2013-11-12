package com.qsoft.pilotproject.model;

import com.qsoft.pilotproject.model.dto.CommentDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 10:06 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseComment
{
    @JsonProperty("data")
    private CommentData commentData;

    public CommentData getCommentData()
    {
        return commentData;
    }

    public void setCommentData(CommentData commentData)
    {
        this.commentData = commentData;
    }

    public List<CommentDTO> getComments()
    {
        return commentData.getCommentDTOs();
    }
}

class CommentData
{
    @JsonProperty("total_comments")
    private int totalComment;
    @JsonProperty("comments")
    private ArrayList<CommentDTO> commentDTOs;

    int getTotalComment()
    {
        return totalComment;
    }

    void setTotalComment(int totalComment)
    {
        this.totalComment = totalComment;
    }

    ArrayList<CommentDTO> getCommentDTOs()
    {
        return commentDTOs;
    }

    void setCommentDTOs(ArrayList<CommentDTO> commentDTOs)
    {
        this.commentDTOs = commentDTOs;
    }
}

