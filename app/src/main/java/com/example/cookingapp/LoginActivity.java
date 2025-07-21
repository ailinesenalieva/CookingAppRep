package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button buttonlogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputemail); //Login mit Email genügt (kein Username)
        inputPassword = findViewById(R.id.inputpassword);
        buttonlogin = findViewById(R.id.buttonlogin);

        mAuth = FirebaseAuth.getInstance();

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                //Überprüfen ob Eingabe vollständig ist
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Sign in with email und password das eingegeben wurde - überprüfen ob die Kombination richtig ist
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class); //wenn erfolgreich einegloggt, dann Weiterleitung zu Home
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                // Intent.FLAG_ACTIVITY_CLEAR_TASK: This flag is used to clear the entire task (stack) of activities associated with the target task before launching a new activity.
                                //Intent.FLAG_ACTIVITY_NEW_TASK: When this flag is used, it creates a new task for the activity, separate from the existing task.
                                //stellt somit sicher dass der Login nicht wieder auftaucht, auch wenn man zurück geht
                                startActivity(intent);
                            } else {
                                //wenn login nicht funktioniert hat
                                Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
