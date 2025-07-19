package com.example.cookingapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String userId;
    public String name;
    public String email;
    public String password;
    private List<String> favorites;
    private List<String> cookedRecipes;
    private List<String> ownrecipes;

    public User() {}

    public User(String userId, String name, String email, String password){
        this.userId = userId;;
        this.name = name;
        this.email=email;
        this.password = password;
        this.favorites = new ArrayList<>();
        this.cookedRecipes = new ArrayList<>();
        this.ownrecipes = new ArrayList<>();
    }
}
