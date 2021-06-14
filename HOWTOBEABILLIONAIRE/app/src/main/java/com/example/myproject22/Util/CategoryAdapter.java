package com.example.myproject22.Util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject22.Model.CategoryClass;
import com.example.myproject22.R;
import com.example.myproject22.View.AddingCategoryActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryClass> categoryList;
    BottomSheetBehavior bottomSheetBehavior;
    TextView tvChooseItem;
    MaterialButton btnAddCategory;
    int id_user;

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

    public CategoryAdapter(ArrayList<CategoryClass> categoryList, BottomSheetBehavior bottomSheetBehavior, TextView tvChooseItem, MaterialButton btnAddCategory) {
        this.categoryList = categoryList;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.tvChooseItem = tvChooseItem;
        this.btnAddCategory = btnAddCategory;
    }

    public CategoryAdapter(ArrayList<CategoryClass> categoryList, BottomSheetBehavior bottomSheetBehavior, TextView tvChooseItem, MaterialButton btnAddCategory, int id_user) {
        this.categoryList = categoryList;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.tvChooseItem = tvChooseItem;
        this.btnAddCategory = btnAddCategory;
        this.id_user = id_user;
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
        btnAddCategory.setEnabled(true);
        btnAddCategory.setVisibility(View.VISIBLE);

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

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior != null)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Intent intent = new Intent(v.getContext(), AddingCategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID_USER", id_user);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
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
