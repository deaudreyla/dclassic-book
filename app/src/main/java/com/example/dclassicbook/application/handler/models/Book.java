package com.example.dclassicbook.application.handler.models;

public class Book {

    private int image;
    private String title;
    private String author;
    private String category;
    private String synopsis;
    private double rating;
    private long userCount;
    private long price;

    public Book(int image, String title, String author, String category, String synopsis,
                 double rating, long userCount, long price) {
        this.image = image;
        this.title = title;
        this.author = author;
        this.category = category;
        this.synopsis = synopsis;
        this.rating = rating;
        this.userCount = userCount;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getRating() {
        return rating;
    }

    public long getUserCount() {
        return userCount;
    }

    public long getPrice() {
        return price;
    }
}
