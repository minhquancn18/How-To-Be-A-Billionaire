package com.example.myproject22.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject22.Model.AddingCategoryClass;
import com.example.myproject22.Model.MoneyCategoryClass;
import com.example.myproject22.R;

import java.util.ArrayList;
import java.util.List;

public class ListAddingCategoryAdapter extends BaseAdapter {

    private ArrayList<AddingCategoryClass> parentList;
    private Context context;

    public ListAddingCategoryAdapter(ArrayList<AddingCategoryClass> parentList, Context context){
        this.parentList = parentList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return parentList.size();
    }

    @Override
    public Object getItem(int position) {
        return parentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_category, null);
        }

        //Đổ dữ liệu vào layout
        TextView tvNameType = convertView.findViewById(R.id.list_item_text);
        tvNameType.setText(this.parentList.get(position).getNameType());

        ImageView ivIcon = convertView.findViewById(R.id.list_item_icon);
        byte[] image = this.parentList.get(position).getImageResource();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        bitmap = Bitmap.createScaledBitmap(bitmap,96,96,true);
        ivIcon.setImageBitmap(bitmap);

        return  convertView;
    }
}
