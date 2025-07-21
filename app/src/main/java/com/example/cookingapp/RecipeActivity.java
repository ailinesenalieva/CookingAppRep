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

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class RecipeActivity extends AppCompatActivity {

    private ImageView recipeimg;
    private TextView recipetitle, ingredients, instructions;
    private Button backtohome, backtoprofile, backtoresults, cookrecipe;
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

        String URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid(); //aktuellen User aus Firebase

        String name = getIntent().getStringExtra("mealName"); //bestimmten mealname bekommen um richtige JSON mit ganzer Information zu erhalten

        if (name == null) {
            Toast.makeText(this, "No Recipe to be found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        String apiUrl = URL + name;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    try {
                        JSONObject meal = response.getJSONArray("meals").getJSONObject(0);
                        recipetitle.setText(meal.getString("strMeal")); //holen uns aus strMeal und strInstructions die Info und setzen als eigene variablen
                        instructions.setText(meal.getString("strInstructions"));

                        StringBuilder ingredientList = new StringBuilder(); //API kann max 20 ingredients auflisten - Iteration aller möglichen Zutaten und Mengeneinheiten
                        for (int i = 1; i <= 20; i++) {
                            String ingredient = meal.getString("strIngredient" + i);
                            String measure = meal.getString("strMeasure" + i);
                            if (!ingredient.isEmpty() && !ingredient.equals("null")) { //wenn sich Inhalt in strIngredient befindet dann wird daraus ein stcihpunkt gemacht und ein zeilenumbruch
                                ingredientList.append("• ").append(measure).append(" ").append(ingredient).append("\n");
                            }
                        }
                        ingredients.setText(ingredientList.toString());

                        imageUrl = meal.getString("strMealThumb");//Bild hinzufügen mit Bitmap
                        new Thread(() -> { //öffnet im Hintergrund eine Verbindung zur Bild URL
                            try {
                                InputStream in = new URL(imageUrl).openStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(in);
                                runOnUiThread(() -> recipeimg.setImageBitmap(bitmap));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();

                        //Fehlerbehandlung
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error while loading the recipe", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error with the API.", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);



        backtoresults.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(RecipeActivity.this, BrowseActivity.class));
             }
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
    }}




