package com.example.myproject22.Util;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject22.R;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {

    ArrayList<byte[]> images;
    ArrayList<String> categoryNames;


    BottomSheetBehavior bottomSheetBehavior;
    TextView tvChooseItem;

    public CategoryItemAdapter(ArrayList<byte[]> images, ArrayList<String> categoryNames, BottomSheetBehavior bottomSheetBehavior, TextView tvChooseItem) {
        this.images = images;
        this.categoryNames = categoryNames;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.tvChooseItem = tvChooseItem;
    }

    public CategoryItemAdapter(ArrayList<byte[]> images, ArrayList<String> categoryNames, BottomSheetBehavior bottomSheetBehavior) {
        this.images = images;
        this.categoryNames = categoryNames;
        this.bottomSheetBehavior = bottomSheetBehavior;
    }

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
        ImageButton categoryImage = ((ImageButton) cardView.findViewById(R.id.categoryImage));
        TextView categoryName = ((TextView) cardView.findViewById(R.id.categoryName));
        Bitmap bitmap = FormatImage.ByteToBitmap(images.get(position));
        categoryImage.setImageBitmap(bitmap);
        categoryName.setText(categoryNames.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior != null)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (tvChooseItem != null)
                    tvChooseItem.setText(categoryName.getText());
            }
        });

        categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior != null)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (tvChooseItem != null)
                    tvChooseItem.setText(categoryName.getText());
            }
        });
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
