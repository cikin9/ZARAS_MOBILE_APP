package com.example.zaras;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    private Context context;

    // Arrays for image names and texts
    private String[] imageNames = {"mira", "syikin", "dan", "zam", "aimie"};
    private String[] texts = {
            "Amira Aliya Binti Mohd Alizam (2021494834)",
            "Nurasyikin Binti Hassan (2021473898)",
            "Muhammad Rasydan Bin Ramdan (2021899442)",
            "Zameer Reeza Bin Zaini (2021494964)",
            "Aimie Batrisya Binti Sham (2021899832)"
    };

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout2, parent, false);
        }

        // Bind data to views
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        // Set image and text
        int imageResId = context.getResources().getIdentifier(imageNames[position], "drawable", context.getPackageName());
        imageView.setImageResource(imageResId);
        textView.setText(texts[position]);

        return convertView;
    }
}
