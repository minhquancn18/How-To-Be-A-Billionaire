package com.example.myproject22.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject22.R;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.myproject22.Util.FormatImage.ByteToBitmap;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {

    ArrayList<byte[]> images;
    ArrayList<String> categoryNames;

    public CategoryItemAdapter(ArrayList<byte[]> images, ArrayList<String> categoryNames) {
        this.images = images;
        this.categoryNames = categoryNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = ((MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_category, parent, false));
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MaterialCardView cardView = holder.cardView;
        ImageButton categoryImage = ((ImageButton)cardView.findViewById(R.id.categoryImage));
        TextView categoryName = ((TextView)cardView.findViewById(R.id.categoryName));
        Bitmap bitmap = ByteToBitmap(images.get(position));
        categoryImage.setImageBitmap(bitmap);
        categoryName.setText(categoryNames.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView cardView;

        public ViewHolder(MaterialCardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

}
