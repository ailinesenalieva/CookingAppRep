package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button buttonhomesearch, buttonprofile, homelogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonhomesearch = findViewById(R.id.buttonhomesearch);
        buttonprofile = findViewById(R.id.buttonprofile);
        homelogout = findViewById(R.id.homelogout);


        //Weiterleitung zum Profil
        buttonprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });
        //Weiterleitung zur Suche von Rezepten
        buttonhomesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, BrowseActivity.class));
            }
        });
        //Weiterleitung zum Logout
        homelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); //logging out
                Intent intent = new Intent(HomeActivity.this, MainActivity.class); //wird wieder zu Main weitergeleitet
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //sodass wieder zurück zu gehen nicht möglich ist
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "You logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
