package com.example.zaras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private DatabaseReference usersReference;
    private FirebaseAuth firebaseAuth;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        String userEmail = getIntent().getStringExtra("userEmail");
        displayUserProfile(userEmail);

        Button viewCartButton = findViewById(R.id.viewCartButton);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to CartActivity
                Intent intent = new Intent(UserProfileActivity.this, CartActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewMenuButton = findViewById(R.id.viewMenu);
        viewMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LayoutActivity
                Intent intent = new Intent(UserProfileActivity.this, LayoutActivity.class);
                // Pass the user email if needed
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView viewAboutButton = findViewById(R.id.imageView3);
        viewAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, AboutActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }

    private void displayUserProfile(final String userEmail) {
        final TextView nameTextView = findViewById(R.id.textViewName);
        final TextView emailTextView = findViewById(R.id.textViewEmail);
        final TextView phoneTextView = findViewById(R.id.textViewPhone);

        usersReference = FirebaseDatabase.getInstance().getReference("Users");
        usersReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);

                        if (user != null) {
                            nameTextView.setText("Name: " + user.getName());
                            emailTextView.setText("Email: " + user.getEmail());
                            phoneTextView.setText("Phone: " + user.getPhone());
                        }
                    }
                } else {
                    // Handle the case where user data for the given email is not found
                    Toast.makeText(UserProfileActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
                Toast.makeText(UserProfileActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}