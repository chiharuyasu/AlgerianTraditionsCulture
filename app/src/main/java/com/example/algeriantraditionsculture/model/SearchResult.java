package com.example.algeriantraditionsculture.model;

public class SearchResult {
    public static final int TYPE_CATEGORY = 1;
    public static final int TYPE_TRADITION = 2;

    private int type;
    private Object data;
    private String title;
    private String description;
    private int imageResourceId;

    public SearchResult(int type, Object data, String title, String description, int imageResourceId) {
        this.type = type;
        this.data = data;
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public int getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
} 