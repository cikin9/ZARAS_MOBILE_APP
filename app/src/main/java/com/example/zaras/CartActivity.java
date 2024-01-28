
package com.example.zaras;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ArrayAdapter<MenuItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        String userEmail = getIntent().getStringExtra("userEmail");

        final List<MenuItem> cartItems = Cart.getItems();



        adapter = new ArrayAdapter<>(this, R.layout.custom_list_item, cartItems);

        ListView listView = findViewById(R.id.listViewCart);
        listView.setAdapter(adapter);

        // Set a click listener for each item in the cart
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showOptionsDialog(position);
            }
        });


        findViewById(R.id.buttonProceedToPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = getIntent().getStringExtra("userEmail");


                proceedToPayment(userEmail);
            }
        });

        Button viewProfileButton = findViewById(R.id.profileButton);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass user email to UserProfileActivity
                Intent intent = new Intent(CartActivity.this, UserProfileActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        Button viewMenuButton = findViewById(R.id.viewMenu);
        viewMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LayoutActivity
                Intent intent = new Intent(CartActivity.this, LayoutActivity.class);
                // Pass the user email if needed
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView viewAboutButton = findViewById(R.id.imageView3);
        viewAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, AboutActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }
    private void updateQuantity(final int position) {
        final MenuItem selectedItem = Cart.getItems().get(position);

        // Use a dialog with a NumberPicker to update the quantity
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Quantity");

        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10); // You can adjust the max quantity as needed
        numberPicker.setValue(selectedItem.getQuantity());

        builder.setView(numberPicker);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newQuantity = numberPicker.getValue();
                selectedItem.setQuantity(newQuantity);

                // Update the adapter to reflect the changes
                adapter.notifyDataSetChanged();


                updateDisplayedPrice();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private void showOptionsDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options")
                .setItems(new CharSequence[]{"Update Quantity", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                updateQuantity(position);
                                break;
                            case 1:
                                deleteItem(position);
                                break;
                        }
                    }
                });
        builder.create().show();
    }


    private View getListViewItem(int position) {
        ListView listView = findViewById(R.id.listViewCart);

        int firstListItemPosition = listView.getFirstVisiblePosition();
        int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void deleteItem(int position) {
        Cart.getItems().remove(position);

        // Update the adapter to reflect the changes
        adapter.notifyDataSetChanged();

        // Update the displayed price in the ListView
        updateDisplayedPrice();
    }

    private void updateDisplayedPrice() {
        // Iterate through the items and update the displayed price in the ListView
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = getListViewItem(i);

            // Assuming there is a default TextView in the layout to display the price
            TextView priceTextView = view.findViewById(android.R.id.text1);

            if (priceTextView != null) {
                double totalPrice = Cart.getItems().get(i).calculateTotalPrice();

                // Format the total price with two decimal places when displaying it
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String formattedPrice = decimalFormat.format(totalPrice);

                // Set the formatted price to the TextView
                priceTextView.setText("Price: RM " + formattedPrice);
            }
        }
    }



    private void proceedToPayment(String userEmail) {
        // Create a string with payment details (customize as needed)
        StringBuilder paymentDetails = new StringBuilder();
        StringBuilder price = new StringBuilder();
        double overallTotalPrice = 0;

        for (MenuItem item : Cart.getItems()) {
            paymentDetails.append(item.getName())
                    .append("\n")
                    .append(" - Quantity: ")
                    .append(item.getQuantity())
                    .append("\n")
                    .append(" -  Price: RM ")
                    .append(String.format("%.2f", item.calculateTotalPrice()))
                    .append("\n")
                    .append("\n");

            // Update overall total price and quantity
//            overallTotalPrice += item.calculateTotalPrice();
//            overallTotalQuantity += item.getQuantity();
        }


        for (MenuItem item : Cart.getItems()) {
            // Update overall total price
            overallTotalPrice += item.calculateTotalPrice();
        }

// Append overall total to price
        price.append(overallTotalPrice);


        // Open the PaymentActivity and pass user email and payment details
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("userEmail", userEmail);
        intent.putExtra("paymentDetails", paymentDetails.toString());
        intent.putExtra("price", price.toString());
        startActivity(intent);
    }
}
