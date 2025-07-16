package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BrowseActivity extends AppCompatActivity {

    private EditText searchinput;
    private Spinner spinnerfilter;
    private Button searchbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        searchinput = findViewById(R.id.searchinput);
        searchbutton = findViewById(R.id.searchbutton);
        String URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";//Datenbank/API mit verschiedneen Rezepten

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = searchinput.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    String finalUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + keyword;

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, finalUrl, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray meals = response.optJSONArray("meals");
                                        if (meals != null && meals.length() > 0) {
                                            ArrayList<HashMap<String, String>> mealList = new ArrayList<>();

                                            for (int i = 0; i < meals.length(); i++) {
                                                JSONObject meal = meals.getJSONObject(i);
                                                String mealName = meal.getString("strMeal");
                                                String mealImage = meal.getString("strMealThumb");

                                                HashMap<String, String> item = new HashMap<>();
                                                item.put("name", mealName);
                                                item.put("imageURL", mealImage);

                                                mealList.add(item);
                                            }

                                            Intent intent = new Intent(BrowseActivity.this, ResultsActivity.class);
                                            intent.putExtra("results", mealList);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(BrowseActivity.this, "No recipes found for your search!", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(BrowseActivity.this, "Error while processing data.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(BrowseActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    Volley.newRequestQueue(BrowseActivity.this).add(request);
                } else {
                    Toast.makeText(BrowseActivity.this, "Please enter a keyword.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    }


