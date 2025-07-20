package com.example.cookingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    private Button logout, createownrecipe;
    private LinearLayout ownrecipesLayout;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logout);
        createownrecipe = findViewById(R.id.createownrecipe);
        ownrecipesLayout = findViewById(R.id.ownrecipesLayout);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (userId == null) {
            Toast.makeText(this, "Error: User is not logged in.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        createownrecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, CreateRecipeActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear back stack
                startActivity(intent);
                Toast.makeText(ProfileActivity.this, "You logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // Rezepte direkt beim Start laden
        loadOwnRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOwnRecipes(); // Aktualisierung um neue eigene Rezepte direkt anzuzeigen
    }

    private void loadOwnRecipes() {
        ownrecipesLayout.removeAllViews(); // leeres Layout, damit keine Duplikate entstehen

        DatabaseReference userRecipesRef = FirebaseDatabase.getInstance().getReference("ownrecipes");
        userRecipesRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot recipeSnap : snapshot.getChildren()) {
                    String title = recipeSnap.child("title").getValue(String.class);
                    String ingredients = recipeSnap.child("ingredients").getValue(String.class);
                    String instructions = recipeSnap.child("instructions").getValue(String.class);
                    String imageUrl = recipeSnap.child("imageUrl").getValue(String.class);

                    OwnRecipes recipe = new OwnRecipes(title, ingredients, instructions, imageUrl, userId);
                    addRecipeToUI(recipe);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error while loading recipes.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRecipeToUI(OwnRecipes recipe) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 0, 0, 40);

        TextView title = new TextView(this);
        title.setText(recipe.getTitle());
        layout.addView(title);

        ImageView img = new ImageView(this);
        layout.addView(img);

        new Thread(() -> {
            try {
                InputStream in = new URL(recipe.imageUrl).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                runOnUiThread(() -> img.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        TextView ingredients = new TextView(this);
        ingredients.setText(String.format("Ingredients: %s", recipe.getIngredients()));
        layout.addView(ingredients);

        TextView instructions = new TextView(this);
        instructions.setText(String.format("Instructions: %s", recipe.getInstructions()));
        layout.addView(instructions);

        ownrecipesLayout.addView(layout);
    }
}
