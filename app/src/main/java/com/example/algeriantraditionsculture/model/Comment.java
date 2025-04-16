package com.example.algeriantraditionsculture.model;

import java.util.Date;

public class Comment {
    private String id;
    private String traditionId;
    private String userId;
    private String userName;
    private String content;
    private Date timestamp;

    public Comment() {
        // Default constructor required for Firebase
    }

    public Comment(String id, String traditionId, String userId, String userName, String content) {
        this.id = id;
        this.traditionId = traditionId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.timestamp = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTraditionId() {
        return traditionId;
    }

    public void setTraditionId(String traditionId) {
        this.traditionId = traditionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
} 