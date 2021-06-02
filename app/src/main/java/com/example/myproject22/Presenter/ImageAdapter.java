package com.example.myproject22.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myproject22.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private ArrayList<byte[]> images;
    private Context context;

    public ImageAdapter(ArrayList<byte[]> images, Context context){
        this.images = images;
        this.context = context;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.image_item_list, null);
        }

        ImageView ivItem = convertView.findViewById(R.id.image_item);
        byte[] image = this.images.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        bitmap = Bitmap.createScaledBitmap(bitmap,96,96,true);
        ivItem.setImageBitmap(bitmap);
        return null;
    }
}
