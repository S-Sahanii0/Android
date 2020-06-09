package com.example.coursework;

public class Menu {
    private String title, price, image;


    public Menu() {
    }

    public Menu(String price, String image, String title) {

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
