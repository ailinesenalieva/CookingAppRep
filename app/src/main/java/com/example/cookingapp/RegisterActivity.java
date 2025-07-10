package com.example.cookingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;
import java.lang.Object;
import android.content.Intent;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;



public class RegisterActivity extends AppCompatActivity {
    //aus register xml importieren
    private EditText inputname;
    private EditText inputemail;
    private EditText inputpassword;
    private Button buttonRegister;
    private DatabaseReference rootDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputname = findViewById(R.id.inputname);
        inputemail=findViewById(R.id.inputemail);
        inputpassword=findViewById(R.id.inputpassword);
        buttonRegister=findViewById(R.id.buttonRegister);
        rootDatabase = FirebaseDatabase.getInstance().getReference().child("user");

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




            }
        }