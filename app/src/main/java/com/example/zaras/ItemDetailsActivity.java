// Inside ItemDetailsActivity.java
package com.example.zaras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ItemDetailsActivity extends AppCompatActivity {

    private MenuItem selectedItem;
    private NumberPicker numberPickerQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);



        // Get the selected MenuItem from Intent
        selectedItem = (MenuItem) getIntent().getSerializableExtra("selectedItem");
        String userEmail = getIntent().getStringExtra("userEmail");


        // Display details in the layout
        ImageView imageView = findViewById(R.id.imageView);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewIngredients = findViewById(R.id.textViewIngredients);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        numberPickerQuantity = findViewById(R.id.numberPickerQuantity);
        Button addToCartButton = findViewById(R.id.addToCartButton);

        // Load image using Picasso library
        Picasso.get().load(selectedItem.getImageUrl()).into(imageView);
        textViewName.setText(selectedItem.getName());
        textViewIngredients.setText("Ingredients: " + selectedItem.getIngredients());
        textViewPrice.setText("Price: RM " + selectedItem.getPrice());

        numberPickerQuantity.setMinValue(1);
        numberPickerQuantity.setMaxValue(10); // You can adjust the max quantity as needed
        numberPickerQuantity.setValue(selectedItem.getQuantity());

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = numberPickerQuantity.getValue();
                selectedItem.setQuantity(quantity);

                Cart.addToCart(selectedItem);
                Toast.makeText(ItemDetailsActivity.this, "Item Added", Toast.LENGTH_SHORT).show();

//
               Intent intent = new Intent(ItemDetailsActivity.this, LayoutActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewCartButton = findViewById(R.id.viewCartButton);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to CartActivity
                Intent intent = new Intent(ItemDetailsActivity.this, CartActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewProfileButton = findViewById(R.id.profileButton);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to UserProfileActivity
                Intent intent = new Intent(ItemDetailsActivity.this, UserProfileActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewMenuButton = findViewById(R.id.viewMenu);
        viewMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LayoutActivity
                Intent intent = new Intent(ItemDetailsActivity.this, LayoutActivity.class);
                // Pass the user email if needed
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView viewAboutButton = findViewById(R.id.imageView3);
        viewAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, AboutActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

    }
}
