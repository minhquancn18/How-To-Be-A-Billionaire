package com.example.myproject22.Util;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject22.Model.CategoryClass;
import com.example.myproject22.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryClass> categoryList;
    BottomSheetBehavior bottomSheetBehavior;
    TextView tvChooseItem;

    public CategoryAdapter(ArrayList<CategoryClass> categoryList, BottomSheetBehavior bottomSheetBehavior, TextView tvChooseItem) {
        this.categoryList = categoryList;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.tvChooseItem = tvChooseItem;
    }

    public CategoryAdapter(ArrayList<CategoryClass> categoryList, BottomSheetBehavior bottomSheetBehavior) {
        this.categoryList = categoryList;
        this.bottomSheetBehavior = bottomSheetBehavior;
    }

    public CategoryAdapter(ArrayList<CategoryClass> categoryList) {
        this.categoryList = categoryList;
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

        Glide.with(cardView.getContext()).load(categoryList.get(position).Get_IMAGE()).into(categoryImage);
        categoryName.setText(categoryList.get(position).Get_NAME());

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
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView cardView;

        public ViewHolder(MaterialCardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

}
