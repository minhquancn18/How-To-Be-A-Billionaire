package com.example.myproject22.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myproject22.Model.AddingCategoryClass;
import com.example.myproject22.Model.IncomeCategoryClass;
import com.example.myproject22.Model.MoneyCategoryClass;
import com.example.myproject22.R;
import com.example.myproject22.View.AddingActivity;

import java.util.ArrayList;
import java.util.HashMap;

//Tạo 1 ExpandableListAdapter để kết nối danh mục thu
public class IncomeCategoryAdapter extends BaseExpandableListAdapter {

    private Context context;

    private ArrayList<IncomeCategoryClass> header;
    private HashMap<String ,ArrayList<IncomeCategoryClass>> child;

    private Intent new_intent;

    public IncomeCategoryAdapter(Context context, ArrayList<IncomeCategoryClass> header, HashMap<String ,ArrayList<IncomeCategoryClass>> child, Intent new_intent) {
        this.context = context;
        this.header = header;
        this.child = child;
        this.new_intent = new_intent;
    }

    @Override
    public int getGroupCount() {
        return this.header.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(this.child.get(this.header.get(groupPosition).Get_NAME()).size() == 0){
            return 0;
        }
        return this.child.get(this.header.get(groupPosition).Get_NAME()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.header.get(groupPosition);
    }

    //Nếu có điều kiện trong trường danh mục chi cha không có danh mục thu con
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (getChildrenCount(groupPosition) == 0) {
            return null;
        } else {
            return this.child.get(this.header.get(groupPosition).Get_NAME()).get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //Đổ dữ liệu của danh mục thu cha vào layout
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_category, null);
        }

        //Đổ dữ liệu vào layout
        TextView tvNameType = convertView.findViewById(R.id.list_item_text);
        tvNameType.setText(this.header.get(groupPosition).Get_NAME());

        ImageView ivIcon = convertView.findViewById(R.id.list_item_icon);

        String simage = this.header.get(groupPosition).Get_IMAGE();
        if (simage != null) {
            byte[] image =  Base64.decode(simage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, true);
            ivIcon.setImageBitmap(bitmap);
        }
        else{
            ivIcon.setImageResource(R.drawable.question);
        }

        //Tạo hàm xử lí sự kiện khi click vào danh mục thu cha
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sMoney="0";
                String sDescription ="";

                Bundle dataBundle = new_intent.getExtras();
                if(dataBundle != null) {
                    sMoney = dataBundle.getString("MoneyText");
                    sDescription = dataBundle.getString("DescriptionText");
                }
                Intent intent = new Intent(v.getContext(), AddingActivity.class);
                //Tạo bundle để truyền dữ liệu để chuyển từ AddingTypeActivity sang AddingAcitivty
                Bundle bundle = new Bundle();
                bundle.putString("MoneyText",sMoney);
                bundle.putString("DescriptionText",sDescription);
                bundle.putInt("addingID", groupPosition);
                //Do khi click vào hàm cha thì ko cần id của thu mục con nên đặt id thư mục con là -1
                bundle.putInt("addingChildID", -1);
                //Gửi dữ liệu để biết thu hay chi
                bundle.putInt("IsType",1);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                ((Activity)context).finish();
            }
        });

        //Để expand toàn bộ expandalbelistview để có thể hiện thị danh mục con nếu ko thì click vào thư mục cha sẽ chuyển qua activity khác mà không hiển thị thư mục con
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);

        return convertView;
    }

    //Đổ dữ liệu của danh mục thu con vào layout
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_category_child, null);
        }
        if(getChildrenCount(groupPosition) != 0) {
            IncomeCategoryClass childClass = child.get(this.header.get(groupPosition).Get_NAME()).get(childPosition);

            TextView tvNameType = convertView.findViewById(R.id.list_item_text_child);
            tvNameType.setText(childClass.Get_NAME());

            ImageView ivIcon = convertView.findViewById(R.id.list_item_icon_child);

            String simage = childClass.Get_IMAGE();
            if (simage != null) {
                byte[] image =  Base64.decode(simage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, true);
                ivIcon.setImageBitmap(bitmap);
            }
            else{
                ivIcon.setImageResource(R.drawable.question);
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
