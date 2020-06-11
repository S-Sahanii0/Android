package com.example.coursework.model;

public class Combo_model {
    private String title, price, image;


    public Combo_model() {
    }

    public Combo_model(String price, String image, String title) {

        this.price = price;
        this.image = image;
        this.title=title;
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
}
