package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button buttonhomesearch;
    private Button buttonprofile;
    private Button homelogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonhomesearch = findViewById(R.id.buttonhomesearch);
        buttonprofile= findViewById(R.id.buttonprofile);
        homelogout = findViewById(R.id.homelogout);

        //weiterführung zu Profile des Users
        buttonprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });
        //weiterführung zur search actiivty
        buttonhomesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this, BrowseActivity.class));
            }
        });
        homelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear back stack
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "You logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}