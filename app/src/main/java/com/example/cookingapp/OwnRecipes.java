package com.example.cookingapp;

public class OwnRecipes {
    public String title, ingredients, instructions, imageUrl, userId;

    public OwnRecipes() {} // Firebase braucht leeren Konstruktor

    public OwnRecipes(String title, String ingredients, String instructions, String imageUrl, String userId) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }
    public String getIngredients(){
        return ingredients;
    }
    public String getInstructions(){
        return instructions;
    }
    public String getImageUrl() {
        return imageUrl;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

