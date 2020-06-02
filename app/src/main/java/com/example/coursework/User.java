package com.example.coursework;

public class User {
    public String username, email, number;


    public User(){

    }

    public User(String username, String email, String number) {
        this.username = username;
        this.email = email;
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }
}
