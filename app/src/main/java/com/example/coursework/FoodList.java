package com.example.coursework;

public class FoodList {

    private String title, price;
    private int image;

    public FoodList( String price, int image, String title) {
        //this.foodid = foodid;
        this.price = price;
        this.image = image;
        this.title=title;
    }



    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
