package com.example.cookingapp;

public class CookedRecipes {
    public String title;
    public String imageUrl;
    public String userId;

    public CookedRecipes() {}

    public CookedRecipes(String title, String imageUrl, String userId) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }
}
