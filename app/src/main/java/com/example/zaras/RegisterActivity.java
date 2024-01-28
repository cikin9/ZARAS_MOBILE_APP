package com.example.zaras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText e1, e2, e3, e4; // Add fields for Name and Phone Number
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e1 = findViewById(R.id.editText); // Email
        e2 = findViewById(R.id.editText2); // Password
        e3 = findViewById(R.id.editTextName); // Name
        e4 = findViewById(R.id.editTextPhone); // Phone Number

        mAuth = FirebaseAuth.getInstance();
    }

    public void createUser(View v) {
        if (e1.getText().toString().equals("") || e2.getText().toString().equals("") ||
                e3.getText().toString().equals("") || e4.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            String email = e1.getText().toString();
            String password = e2.getText().toString();
            String name = e3.getText().toString(); // Get Name
            String phone = e4.getText().toString(); // Get Phone Number

            // Update Firebase authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Update Firebase Realtime Database with user details
                                String userId = mAuth.getCurrentUser().getUid();
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(userId)
                                        .setValue(new User(name, email, phone));

                                Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "User could not be created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
