package com.example.zaras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderReceivedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_received);

        Button backToMenuButton = findViewById(R.id.backtomenu1);

        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the cart when Order Received button is clicked
                Cart.clearCart();

                String userEmail = getIntent().getStringExtra("userEmail");

                // Assuming you want to go back to the LayoutActivity, replace with the correct activity class
                Intent intent = new Intent(OrderReceivedActivity.this, LayoutActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }
}
