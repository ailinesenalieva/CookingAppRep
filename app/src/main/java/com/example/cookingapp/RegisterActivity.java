package com.example.cookingapp;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
    //elemente aus xml entnehmen
    private EditText inputname;
    private EditText inputemail;
    private EditText inputpassword;
    private Button buttonRegister;
    //database anbinfung
    private DatabaseReference rootDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputname = findViewById(R.id.inputname);
        inputemail = findViewById(R.id.inputemail);
        inputpassword = findViewById(R.id.inputpassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        rootDatabase = FirebaseDatabase.getInstance().getReference("user");
    //wenn register button gedrückt wird -> prüfen ob alles gefüllt ist dann soll in db gespeichert werden
        //sonst aufforderung zum ausfüllen
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputname.getText().toString().trim();
                String email = inputemail.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all of your information!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userID = rootDatabase.push().getKey();
                User user = new User(name, email, password);
                rootDatabase.child(userID).setValue(user);

                Toast.makeText(RegisterActivity.this, "You registered successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
