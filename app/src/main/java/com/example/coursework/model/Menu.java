package com.example.coursework.model;

public class Menu {
    private String title, price, image, key;


    public Menu() {
    }

    public Menu(String price, String image, String title) {

        this.price = price;
        this.image = image;
        this.title=title;
    }

    public Menu(String price,  String image, String title, String key) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
