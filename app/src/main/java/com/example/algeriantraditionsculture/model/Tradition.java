package com.example.algeriantraditionsculture.model;

public class Tradition {
    private int id;
    private String title;
    private String description;
    private String historicalBackground;
    private String imageUrl;
    private String videoUrl;
    private String audioUrl;
    private boolean isFavorite;
    private String region;  // Algerian region where the tradition is practiced
    private String season;  // Season or time of year when the tradition is celebrated
    private String[] ingredients;  // For food traditions
    private String[] steps;  // For traditions with specific steps or rituals
    private String[] relatedTraditions;  // Related traditions or customs

    public Tradition(int id, String title, String description, String historicalBackground,
                    String imageUrl, String videoUrl, String audioUrl, String region,
                    String season, String[] ingredients, String[] steps, String[] relatedTraditions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.historicalBackground = historicalBackground;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.audioUrl = audioUrl;
        this.region = region;
        this.season = season;
        this.ingredients = ingredients;
        this.steps = steps;
        this.relatedTraditions = relatedTraditions;
        this.isFavorite = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getHistoricalBackground() {
        return historicalBackground;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getRegion() {
        return region;
    }

    public String getSeason() {
        return season;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String[] getSteps() {
        return steps;
    }

    public String[] getRelatedTraditions() {
        return relatedTraditions;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
} 