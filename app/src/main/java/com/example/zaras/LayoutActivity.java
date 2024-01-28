// Inside LayoutActivity.java
package com.example.zaras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import com.google.firebase.FirebaseApp;
import android.view.View;
import android.widget.ImageView;

public class LayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        GridView gridView = findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);
        String userEmail = getIntent().getStringExtra("userEmail");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(LayoutActivity.this, ItemDetailsActivity.class);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("selectedItem", imageAdapter.menuItems.get(position));
                startActivity(intent);
            }
        });

        Button viewCartButton = findViewById(R.id.viewCartButton);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to CartActivity
                Intent intent = new Intent(LayoutActivity.this, CartActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewProfileButton = findViewById(R.id.profileButton);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to UserProfileActivity
                Intent intent = new Intent(LayoutActivity.this, UserProfileActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView viewAboutButton = findViewById(R.id.imageView3);
        viewAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LayoutActivity.this, AboutActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }
}
