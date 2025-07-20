package com.example.cookingapp;

import android.content.SharedPreferences;
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


        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (userId == null) {
            Toast.makeText(this, "Error: the user is not logged in.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        inputTitle = findViewById(R.id.inputTitle);
        inputIngredients = findViewById(R.id.inputIngredients);
        inputInstructions = findViewById(R.id.inputInstructions);
        inputImageUrl = findViewById(R.id.inputImageUrl);
        saveRecipeBtn = findViewById(R.id.saveRecipeBtn);

        recipeRef = FirebaseDatabase.getInstance().getReference("ownrecipes");
        userRef = FirebaseDatabase.getInstance().getReference("user").child(userId);

        saveRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = recipeRef.push().getKey();
                if (id == null) return;

                OwnRecipes recipe = new OwnRecipes(

                        inputTitle.getText().toString(),
                        inputIngredients.getText().toString(),
                        inputInstructions.getText().toString(),
                        inputImageUrl.getText().toString(),
                        userId
                );

                recipeRef.child(id).setValue(recipe);
                userRef.child("ownrecipes").child(id).setValue(true);

                Toast.makeText(CreateRecipeActivity.this, "Recipe saved successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
