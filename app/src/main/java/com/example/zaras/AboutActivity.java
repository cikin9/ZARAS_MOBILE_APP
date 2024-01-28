package com.example.zaras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.firebase.FirebaseApp;
import android.view.View;
import android.widget.GridView;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        GridView gridView = findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(this);
        gridView.setAdapter(adapter);
        FirebaseApp.initializeApp(this);

        String userEmail = getIntent().getStringExtra("userEmail");

        Button viewCartButton = findViewById(R.id.viewCartButton);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to CartActivity
                Intent intent = new Intent(AboutActivity.this, CartActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewProfileButton = findViewById(R.id.profileButton);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to UserProfileActivity
                Intent intent = new Intent(AboutActivity.this, UserProfileActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewMenuButton = findViewById(R.id.viewMenu);
        viewMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LayoutActivity
                Intent intent = new Intent(AboutActivity.this, LayoutActivity.class);
                // Pass the user email if needed
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

    }

}
