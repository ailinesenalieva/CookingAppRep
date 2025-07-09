package com.example.cookingapp;

//hiermit kann man die Rezepte aus https://www.themealdb.com/api.php laden
public class Recipe {
    public String id;
    public String name;
    public String category;
    public String instructions;
    public String imageUrl;

    public Recipe() {
    } // Für Firebase notwendig

    public Recipe(String id, String name, String category, String instructions, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
    }
}
