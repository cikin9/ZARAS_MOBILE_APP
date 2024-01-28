package com.example.zaras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private TextView userEmailTextView;
    private TextView userNameTextView;
    private TextView userPhoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        databaseReference = FirebaseDatabase.getInstance().getReference("payments");
        firebaseAuth = FirebaseAuth.getInstance();
        String paymentId = databaseReference.push().getKey();

        userEmailTextView = findViewById(R.id.textViewUserEmail);
        userNameTextView = findViewById(R.id.textViewUserName);
        userPhoneTextView = findViewById(R.id.textViewUserPhone);

        String userEmail = getIntent().getStringExtra("userEmail");
        getUserInfo(userEmail);

        TextView paymentDetailsTextView = findViewById(R.id.textViewPaymentDetails);
        String paymentDetails2 = getIntent().getStringExtra("paymentDetails");
        paymentDetailsTextView.setText("Payment Details:\n" + paymentDetails2);

        EditText addressEditText = findViewById(R.id.editTextAddress);

        Spinner paymentMethodSpinner = findViewById(R.id.spinnerPaymentMethod);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.payment_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(adapter);

        TextView pricedetails = findViewById(R.id.pdetails);
        String priceStr = getIntent().getStringExtra("price");
        double price = Double.parseDouble(priceStr);
        pricedetails.setText("Total Price: RM " + String.format("%.2f", price));

        Button viewCartButton = findViewById(R.id.viewCartButton);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to CartActivity
                Intent intent = new Intent(PaymentActivity.this, CartActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewProfileButton = findViewById(R.id.profileButton);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to UserProfileActivity
                Intent intent = new Intent(PaymentActivity.this, UserProfileActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewMenuButton = findViewById(R.id.viewMenu);
        viewMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LayoutActivity
                Intent intent = new Intent(PaymentActivity.this, LayoutActivity.class);
                // Pass the user email if needed
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView viewAboutButton = findViewById(R.id.imageView3);
        viewAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, AboutActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }

    private void getUserInfo(String userEmail) {
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users");

        usersReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);

                        if (user != null) {
                            userEmailTextView.setText("User Email: " + user.getEmail());
                            userNameTextView.setText("User Name: " + user.getName());
                            userPhoneTextView.setText("User Phone: " + user.getPhone());
                        }
                    }
                } else {
                    // Handle the case where user data for the given email is not found
                    Toast.makeText(PaymentActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
                Toast.makeText(PaymentActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void proceedToSuccess(View view) {
        EditText addressEditText = findViewById(R.id.editTextAddress);
        String address = addressEditText.getText().toString().trim();

        Spinner paymentMethodSpinner = findViewById(R.id.spinnerPaymentMethod);
        String selectedPaymentMethod = paymentMethodSpinner.getSelectedItem().toString();

        String userEmail = getIntent().getStringExtra("userEmail");
        String status = "Haven't Received";

        // Save payment details to Firebase
        String paymentDetails2 = getIntent().getStringExtra("paymentDetails");
        String priceStr = getIntent().getStringExtra("price");
        double price = Double.parseDouble(priceStr);

        // Save payment details and get the generated paymentId
        String paymentId = savePaymentDetails(userEmail, address, selectedPaymentMethod, paymentDetails2, price, status);

        Toast.makeText(this, "User Email: " + userEmail + ", Price: " + String.format("%.2f", price), Toast.LENGTH_SHORT).show();

        // Pass paymentId to the SuccessActivity
        Intent intent = new Intent(this, SuccessActivity.class);
        intent.putExtra("paymentId", paymentId);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }

    private String savePaymentDetails(String userEmail, String address, String paymentMethod, String paymentDetails2, double price, String status) {
        String paymentId = databaseReference.push().getKey();

        PaymentDetails paymentDetails = new PaymentDetails(userEmail, address, paymentMethod, paymentDetails2, price, status);

        databaseReference.child(paymentId).setValue(paymentDetails);

        return paymentId;
    }
}
