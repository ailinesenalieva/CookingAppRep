package com.example.cookingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class RecipeActivity extends AppCompatActivity {

    private ImageView recipeimg;
    private TextView recipetitle, ingredients, instructions;
    private Button backtohome, backtoprofile, backtoresults, cookrecipe;

    private DatabaseReference cookedRef, ownRef;
    private boolean isOwnRecipe = false;
    private String imageUrl;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeimg = findViewById(R.id.recipeimg);
        recipetitle = findViewById(R.id.recipetitle);
        ingredients = findViewById(R.id.ingredients);
        instructions = findViewById(R.id.instructions);
        backtoresults = findViewById(R.id.backtoresults);
        backtohome = findViewById(R.id.backtohome);
        backtoprofile = findViewById(R.id.backtoprofile);


        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid(); //aktuellen User aus Firebase
        ownRef = FirebaseDatabase.getInstance().getReference("ownrecipes");

        // Daten aus vorheriger Activity holen
        isOwnRecipe = getIntent().getBooleanExtra("isOwnRecipe", false);

        if (isOwnRecipe) {
            loadOwnRecipe();
        } else {
            String mealName = getIntent().getStringExtra("mealname");
            if (mealName != null && !mealName.isEmpty()) {
                loadMealDetails(mealName);
            }
        }

        backtoresults.setOnClickListener(v -> {
            String searchKeyword = getIntent().getStringExtra("searchTerm");
            Intent intent = new Intent(RecipeActivity.this, ResultsActivity.class);
            intent.putExtra("searchTerm", searchKeyword);
            startActivity(intent);
            finish();
        });

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View v) {
                  startActivity(new Intent(RecipeActivity.this, HomeActivity.class));
                                          }
        });

        backtoprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipeActivity.this, ProfileActivity.class));
            }
        });


    }

    private void loadOwnRecipe() {
        String title = getIntent().getStringExtra("title");
        String ing = getIntent().getStringExtra("ingredients");
        String instr = getIntent().getStringExtra("instructions");
        imageUrl = getIntent().getStringExtra("imageUrl");

        recipetitle.setText(title);
        ingredients.setText(ing);
        instructions.setText(instr);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            new Thread(() -> {
                try {
                    InputStream in = new URL(imageUrl).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    runOnUiThread(() -> recipeimg.setImageBitmap(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void loadMealDetails(String name) {
        String apiUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + name;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    try {
                        JSONObject meal = response.getJSONArray("meals").getJSONObject(0);
                        recipetitle.setText(meal.getString("strMeal"));
                        instructions.setText(meal.getString("strInstructions"));

                        StringBuilder ingredientList = new StringBuilder();
                        for (int i = 1; i <= 20; i++) {
                            String ingredient = meal.getString("strIngredient" + i);
                            String measure = meal.getString("strMeasure" + i);
                            if (!ingredient.isEmpty() && !ingredient.equals("null")) {
                                ingredientList.append("• ").append(measure).append(" ").append(ingredient).append("\n");
                            }
                        }
                        ingredients.setText(ingredientList.toString());

                        imageUrl = meal.getString("strMealThumb");
                        new Thread(() -> {
                            try {
                                InputStream in = new URL(imageUrl).openStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(in);
                                runOnUiThread(() -> recipeimg.setImageBitmap(bitmap));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Fehler beim Laden des Rezepts", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Fehler bei der Verbindung zur API", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void saveOwnRecipe() {
        String id = ownRef.push().getKey();
        if (id == null) return;

        OwnRecipes recipe = new OwnRecipes(
                recipetitle.getText().toString(),
                ingredients.getText().toString(),
                instructions.getText().toString(),
                imageUrl,
                currentUserId
        );

        ownRef.child(id).setValue(recipe);
        Toast.makeText(this, "Eigenes Rezept gespeichert!", Toast.LENGTH_SHORT).show();
    }
}
