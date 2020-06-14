package com.example.coursework.model;

public class User {
    public String username, email, number, name, image;


    public User(){

    }

    public User(String username, String email, String number, String name, String image) {
        this.username = username;
        this.email = email;
        this.number = number;
        this.name= name;
        this.image=image;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
