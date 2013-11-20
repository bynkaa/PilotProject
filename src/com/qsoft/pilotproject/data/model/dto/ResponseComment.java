package com.qsoft.pilotproject.data.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.pilotproject.data.model.entity.CommentCC;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 10:06 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseComment {
    @JsonProperty("data")
    private CommentData commentData;

    public CommentData getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentData commentData) {
        this.commentData = commentData;
    }

    public List<CommentCC> getComments() {
        return commentData.getCommentDTOs();
    }
}

class CommentData {
    @JsonProperty("total_comments")
    private int totalComment;
    @JsonProperty("comments")
    private ArrayList<CommentCC> commentDTOs;

    int getTotalComment() {
        return totalComment;
    }

    void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    ArrayList<CommentCC> getCommentDTOs() {
        return commentDTOs;
    }

    void setCommentDTOs(ArrayList<CommentCC> commentDTOs) {
        this.commentDTOs = commentDTOs;
    }
}

