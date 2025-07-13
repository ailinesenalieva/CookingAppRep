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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity {

    private EditText searchinput;
    private Spinner spinnerfilter;
    private Button searchbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        searchinput=findViewById(R.id.searchinput);
        spinnerfilter=findViewById(R.id.spinnerfilter);
        searchbutton=findViewById(R.id.searchbutton);
        String URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";//Datenbank/API mit verschiedneen Rezepten

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String keyword = searchinput.getText().toString().trim();
                //Überprüfen ob user keyword eingegeben hat
               if(!keyword.isEmpty()){
                   String finalUrl = URL + keyword;// Beispiel https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata sucht nach arrabiata
                    //JSONObject Request ist nützlcih um die Daten aus dem Web/DAtenebank zu
                   JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                       @Override
                       public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals = response.optJSONArray("meals");
                            if (meals!= null){
                                ArrayList<String> mealnames = new ArrayList<>();
                                for (int i =0; i<meals.length(); i++){
                                    JSONObject meal = meals.getJSONObject(i);
                                    String mealname = meal.getString("strMeal");
                                    mealnames.add(mealname);
                                }
                                Intent intent = new Intent(BrowseActivity.this, ResultsActivity.class);
                                intent.putStringArrayListExtra("results", mealnames);
                                startActivity(intent);
                            } else {
                                Toast.makeText(BrowseActivity.this, "No recipes for your search found! Sorry.", Toast.LENGTH_SHORT).show();
                        }
                       } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(BrowseActivity.this, "Error while processing data.", Toast.LENGTH_SHORT).show();
                        }
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Toast.makeText(BrowseActivity.this, "There is a network error, Please try again.", Toast.LENGTH_SHORT).show();
                       }
                   });
               }
               else{
                   Toast.makeText(BrowseActivity.this, "Please enter another keyword.", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

}
