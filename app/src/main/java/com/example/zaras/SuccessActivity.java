package com.example.zaras;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import android.view.View;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SuccessActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);


        databaseReference = FirebaseDatabase.getInstance().getReference("payments");
        Button orderReceivedButton = findViewById(R.id.buttonOrderReceived);

        orderReceivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateOrderReceived();
            }
        });
    }


    private void updateOrderReceived() {

        String paymentId = getIntent().getStringExtra("paymentId");
        String userEmail = getIntent().getStringExtra("userEmail");

        databaseReference.child(paymentId).child("status").setValue("Order Received");


        Toast.makeText(this, "Order Received. Updating Database...", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(this, OrderReceivedActivity.class);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }

}
