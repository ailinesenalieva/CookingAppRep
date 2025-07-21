package com.example.cookingapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String userId;
    public String name;
    public String email;
    public String password;

    private List<String> ownrecipes;

    public User() {}

    public User(String userId, String name, String email, String password){
        this.userId = userId;;
        this.name = name;
        this.email=email;
        this.password = password;
        this.ownrecipes = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public List<String> getOwnrecipes() {
        return ownrecipes;
    }

    public void setOwnrecipes(List<String> ownrecipes) {
        this.ownrecipes = ownrecipes;
    }

    public void addOwnRecipe(String recipeId) {
        if (this.ownrecipes == null) {
            this.ownrecipes = new ArrayList<>();
        }
        this.ownrecipes.add(recipeId);
    }
}

