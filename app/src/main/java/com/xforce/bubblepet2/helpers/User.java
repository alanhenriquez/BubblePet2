package com.xforce.bubblepet2.helpers;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String title;
    public String content;

    public User() {
    }

    public User(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
