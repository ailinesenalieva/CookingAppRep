package com.example.cookingapp;

public class OwnRecipes {
    private String id;
    private String userId;
    private String name;
    private String ingredients;
    private String instructions;
    private String imageUrl;

    public OwnRecipes() {}

    public OwnRecipes(String id, String userId, String name, String ingredients, String instructions, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
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

