package com.example.cookingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;
import java.lang.Object;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;



public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button RegisterButton = findViewById(R.id.buttonreg);




        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {


            }
        });
    }
}