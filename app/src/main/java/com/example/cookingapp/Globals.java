package com.example.cookingapp;

public class Globals {
    private static final Globals instance = new Globals();
    private String currentUser;
    //privater Konstruktor (von außen keine neue Instanzen möglich)
    private Globals(){

    }
    public static Globals getInstance(){
        return instance;
    }
    public void setCurrentUser(String username){
        this.currentUser = username;
    }
    public String getCurrentUser(){
        return currentUser;
    }


}
