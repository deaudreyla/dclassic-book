package com.example.dclassicbook.application.handler.models;

public class Store {

    private int image;
    private String name;
    private String location;

    public Store(int image, String name, String location) {
        this.image = image;
        this.name = name;
        this.location = location;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
