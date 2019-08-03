package com.mygdx.game;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    public int money;
    public int nuggetsAmount;


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(int money, int nuggetsAmount) {
        this.money = money;
        this.nuggetsAmount = nuggetsAmount;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", money);
        result.put("author", nuggetsAmount);

        return result;
    }

}

