package com.example.learn_english_smart;

import android.net.Uri;

public class User {

    public String username;
    public String email;
    public String image;
    public int lever;
    public int exp;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String image, int lever, int exp) {
        this.username = username;
        this.email = email;
        this.image = image;
        this.lever = lever;
        this.exp = exp;
    }
}

