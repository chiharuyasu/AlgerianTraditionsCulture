package com.example.algeriantraditionsculture.model;

public class Category {
    private int id;
    private String name;
    private String description;
    private int imageResourceId;

    public Category(int id, String name, String description, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
} 