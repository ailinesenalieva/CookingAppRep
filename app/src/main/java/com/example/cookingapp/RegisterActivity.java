package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputname, inputemail, inputpassword;
    private Button buttonRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputname = findViewById(R.id.inputname);
        inputemail = findViewById(R.id.inputemail);
        inputpassword = findViewById(R.id.inputpassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("user"); //in Firebase Realtime DB wurde USER Objekt erstellt und je user gibt es Parameter wie aus der USer klasse

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputname.getText().toString().trim();
                String email = inputemail.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();

                //Überprüfen ob EIngaben leer sind
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //erstellen einen neuen Useraccount mit Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                if (firebaseUser != null) {
                                    String userId = firebaseUser.getUid();//erhält eine eigene UserID


                                    // Speichere User in der Realtime Database - user objekt erstellen
                                    User user = new User(userId, name, email, password);
                                    //gespeicherter User unter der generierten ID speichern
                                    userRef.child(userId).setValue(user).addOnCompleteListener(dbTask -> {
                                                if (dbTask.isSuccessful()) { //Weiterletung zu Home Activity wenn alles korrekt gelaufen ist
                                                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                                    finish();
                                                } else { //wenn Firebase nicht die User Daten speichern konnte
                                                    Toast.makeText(RegisterActivity.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                                }
                                                } else { //wenn die Register an sich nicht funktionietz hat + Grund (falsches Format z.B.)
                                                    Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
