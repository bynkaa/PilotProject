package com.qsoft.pilotproject.ui.model;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class UiFeedModel {
    String title;
    String displayName;
    String like;
    String comment;
    String updateTime;

    public UiFeedModel(String title, String displayName, String like, String comment, String updateTime) {
        this.title = title;
        this.displayName = displayName;
        this.like = like;
        this.comment = comment;
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
