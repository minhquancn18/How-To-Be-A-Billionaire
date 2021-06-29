package com.example.myproject22.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myproject22.Model.CategoryClass;
import com.example.myproject22.Presenter.Interface.AddingMoneyInterface;
import com.example.myproject22.R;
import com.example.myproject22.View.Activity.AddingCategoryActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>  {

    //region Component
    ArrayList<CategoryClass> categoryList;
    BottomSheetBehavior bottomSheetBehavior;
    TextView tvChooseItem;
    MaterialButton btnAddCategory;
    int id_user;
    Context context;
    //endregion

    public CategoryAdapter(Context context, ArrayList<CategoryClass> categoryList, BottomSheetBehavior bottomSheetBehavior,
                           TextView tvChooseItem, MaterialButton btnAddCategory, int id_user) {
        this.context = context;
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

        //region Component
        MaterialCardView cardView = holder.cardView;
        ImageButton categoryImage = ((ImageButton) cardView.findViewById(R.id.categoryImage));
        TextView categoryName = ((TextView) cardView.findViewById(R.id.categoryName));
        btnAddCategory.setEnabled(true);
        btnAddCategory.setVisibility(View.VISIBLE);
        //endregion

        //region Gán giá trị
        FormatImage.LoadImageIntoView(categoryImage,cardView.getContext(),categoryList.get(position).Get_IMAGE());
        categoryName.setText(categoryList.get(position).Get_NAME());
        //endregion

        //region Handle click
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
                if(context instanceof AddingMoneyInterface){
                    ((AddingMoneyInterface) context).ResetSound();
                }
                Intent intent = new Intent(v.getContext(), AddingCategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID_USER", id_user);
                intent.putExtras(bundle);
                ((Activity)v.getContext()).startActivity(intent);
                ((Activity)v.getContext()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
        });
        //endregion
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
