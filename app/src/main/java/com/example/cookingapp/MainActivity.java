package com.example.cookingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mini speicher für user um zu gucken ob dieseer eingeloggt ist
        SharedPreferences pr = getSharedPreferences("user", MODE_PRIVATE);
        boolean loggedIn = pr.getBoolean("loggedIn", false);

        if(loggedIn){
            startActivity(new Intent(this, HomeActivity.class));
        } else{
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
        }

    }

