package com.example.zaras;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private static final String TAG = "ImageAdapter";

    private Context mContext;
    public List<MenuItem> menuItems;

    public ImageAdapter(Context c) {
        mContext = c;
        menuItems = new ArrayList<>();
        fetchMenuItems();
    }

    private void fetchMenuItems() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("menu");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuItems.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = itemSnapshot.child("imageUrl").getValue(String.class);
                    String ingredients = itemSnapshot.child("ingredients").getValue(String.class);
                    String name = itemSnapshot.child("name").getValue(String.class);

                    // Check if price is null before conversion
                    Double price = itemSnapshot.child("price").getValue(Double.class);
                    if (price == null) {
                        // Handle the case where price is null, you can set a default value or skip the item
                        Log.e(TAG, "Price is null for item: " + name);
                        continue;
                    }

                    // Update the image URL to use Firebase Storage URL
                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/zaras-4926d.appspot.com/o/" + imageUrl + "?alt=media";

                    MenuItem menuItem = new MenuItem(imageUrl, ingredients, name, price);
                    menuItems.add(menuItem);

                    Log.d(TAG, "MenuItem: " + menuItem.getName() + ", " + menuItem.getIngredients() + ", " + menuItem.getPrice());
                }

                notifyDataSetChanged();
                Log.d(TAG, "Menu items fetched successfully. Count: " + menuItems.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Firebase error: " + databaseError.getMessage());
                // Handle error
            }
        });
    }


    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position).getImageUrl();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_layout, null);
        }

        ImageView imageView = convertView.findViewById(R.id.gridItemImage);
        TextView textView = convertView.findViewById(R.id.gridItemText);

        // Load image using Picasso library
        Picasso.get().load(menuItems.get(position).getImageUrl())
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Image loaded successfully");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Error loading image: " + e.getMessage());
                    }
                });

        textView.setText(menuItems.get(position).getName());

        return convertView;
    }
}
