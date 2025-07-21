package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso; // Image-Loading-Bibliothek für Android um Bild URLS einfach einfügen zu können

import java.util.ArrayList;
import java.util.HashMap;

public class ResultsActivity extends AppCompatActivity {

    private LinearLayout resultslayout;
    private Button backtobrowse, backhome, backprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultslayout = findViewById(R.id.resultslayout);
        backtobrowse = findViewById(R.id.backtobrowse);
        backhome = findViewById(R.id.backhome);
        backprofile = findViewById(R.id.backprofile);

        //mealList aus Browse Activity die als Extra mitgegebne wurden
        ArrayList<HashMap<String, String>> mealList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("results");

        //wenn meallist inhalt besitzt, wird durch jedes Hashmap paar iteriert und zu resultcard methode angezeigt
        if (mealList != null && !mealList.isEmpty()) {
            for (HashMap<String, String> meal : mealList) {
                String name = meal.get("name");
                String imageUrl = meal.get("imageURL");
                addResultCard(meal);
            }
        } else { //wenn keine resultate für die suche ergeben
            TextView noResult = new TextView(this);
            noResult.setText(getString(R.string.norecipes));
            resultslayout.addView(noResult);
        }

        backtobrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsActivity.this, BrowseActivity.class));
                finish();
            }});

        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsActivity.this, HomeActivity.class));
            }});
        backprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsActivity.this, ProfileActivity.class));
            }});
    }


    //Rezepte aus meallist mit namen und bild werden horizontal aufgestellt
    private void addResultCard(HashMap<String, String> meal) {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setPadding(0, 0, 0, 16);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        LinearLayout card = createCard(meal); //einzelne rezepte (name + bild nebeneinander)
        rowLayout.addView(card);
        resultslayout.addView(rowLayout); //zu gesamten view hinzufügen
    }




    private LinearLayout createCard(HashMap<String, String> meal) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(8, 8, 8, 8);
        card.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        String mealName = meal.get("name");
        String imageUrl = meal.get("imageURL");
        ImageView image = new ImageView(this);
        image.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 300));
        Picasso.get().load(imageUrl).into(image);
        card.addView(image);

        TextView title = new TextView(this);
        title.setText(mealName);
        title.setTextSize(16f);
        card.addView(title);

        card.setOnClickListener(view -> {
            Intent intent = new Intent(ResultsActivity.this, RecipeActivity.class);
            intent.putExtra("mealname", meal.get("name"));

            startActivity(intent);
        });

        return card;
    }
}
