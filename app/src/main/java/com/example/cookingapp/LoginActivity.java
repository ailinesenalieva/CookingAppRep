package com.example.cookingapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    //objekte aus xml
    private EditText inputemail;
    private EditText inputpassword;
    private Button buttonlogin;
    private DatabaseReference rootDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputemail = findViewById(R.id.inputemail);
        inputpassword = findViewById(R.id.inputpassword);
        buttonlogin = findViewById(R.id.buttonlogin);
        rootDatabase = FirebaseDatabase.getInstance().getReference("user");

        //Button wird gedrückt wenn eingeloggt
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputemail.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email and password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                rootDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean loginOk = false;
                        for (DataSnapshot userSnap : snapshot.getChildren()) {
                            User user = userSnap.getValue(User.class);
                            if (user != null && user.email.equals(email) && user.password.equals(password)) {
                                loginOk = true;
                                break;
                            }}
                        if (loginOk) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "E-Mail or password wrong", Toast.LENGTH_SHORT).show();
                        }}
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "Error in database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
// weiterleitung zu home





}