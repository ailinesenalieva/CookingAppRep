package com.example.cookingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRecipeActivity extends AppCompatActivity {

    private EditText inputTitle, inputIngredients, inputInstructions, inputImageUrl;
    private Button saveRecipeBtn;
    private DatabaseReference recipeRef, userRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        //holt sich die ID vom aktuell eingeloggten/registrierten User
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        inputTitle = findViewById(R.id.inputTitle);
        inputIngredients = findViewById(R.id.inputIngredients);
        inputInstructions = findViewById(R.id.inputInstructions);
        inputImageUrl = findViewById(R.id.inputImageUrl);
        saveRecipeBtn = findViewById(R.id.saveRecipeBtn);

        recipeRef = FirebaseDatabase.getInstance().getReference("ownrecipes"); //ownrecipes ist eine eigene Entität und darauf wird referenziert
        userRef = FirebaseDatabase.getInstance().getReference("user").child(userId); //userid (attribut) aus dem aktuellen User (Entität) wird mit Rezept zugeordnet

        saveRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = recipeRef.push().getKey(); //zu jedem neuen Rezept wird eine ID erstellt
                if (id == null) return;

                OwnRecipes recipe = new OwnRecipes( //neues OwnRecipes Objekt wird erstellt mit allen Parametern aus der Klasse
                        inputTitle.getText().toString(),
                        inputIngredients.getText().toString(),
                        inputInstructions.getText().toString(),
                        inputImageUrl.getText().toString(),
                        userId
                );

                recipeRef.child(id).setValue(recipe); //neues Rezept Objekt wird in Firebase unter ownrecipes gespeichert
                userRef.child("ownrecipes").child(id).setValue(true); //rezept id wird unter der jeweiligen user id gespeichert

                Toast.makeText(CreateRecipeActivity.this, "Recipe saved successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
