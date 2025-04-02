package com.example.algeriantraditionsculture.model;

public class Tradition {
    private int id;
    private int categoryId;
    private String title;
    private String description;
    private String historicalBackground;
    private int imageResourceId;
    private String videoUrl;
    private String audioUrl;
    private String location;
    private boolean isFavorite;

    public Tradition(int id, int categoryId, String title, String description, 
                    String historicalBackground, int imageResourceId, 
                    String videoUrl, String audioUrl, String location) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.historicalBackground = historicalBackground;
        this.imageResourceId = imageResourceId;
        this.videoUrl = videoUrl;
        this.audioUrl = audioUrl;
        this.location = location;
        this.isFavorite = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHistoricalBackground() {
        return historicalBackground;
    }

    public void setHistoricalBackground(String historicalBackground) {
        this.historicalBackground = historicalBackground;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
} 