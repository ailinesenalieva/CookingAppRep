package com.example.cookingapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button LoginButton = findViewById(R.id.buttonlog);
        LoginButton.setOnClickListener();
    }
}