package com.example.coursework;

public class User {
    public String username, email, number, name;


    public User(){

    }

    public User(String username, String email, String number, String name) {
        this.username = username;
        this.email = email;
        this.number = number;
        this.name= name;
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
}
