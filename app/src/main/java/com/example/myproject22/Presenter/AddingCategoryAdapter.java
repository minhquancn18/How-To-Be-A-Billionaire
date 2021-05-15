package com.example.myproject22.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject22.Model.AddingCategoryClass;
import com.example.myproject22.R;
import com.example.myproject22.View.AddingActivity;

import java.util.ArrayList;
import java.util.HashMap;

//Tạo 1 ExpandableListAdapter để kết nối danh mục thu
public class AddingCategoryAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<AddingCategoryClass> headerAddingCategoryClass;
    private HashMap<String, ArrayList<AddingCategoryClass>> childAddingCategory;

    public AddingCategoryAdapter(Context context, ArrayList<AddingCategoryClass> headerAddingCategoryClass, HashMap<String, ArrayList<AddingCategoryClass>> childAddingType) {
        this.context = context;
        this.headerAddingCategoryClass = headerAddingCategoryClass;
        this.childAddingCategory = childAddingType;
    }

    @Override
    public int getGroupCount() {
        return this.headerAddingCategoryClass.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childAddingCategory.get(this.headerAddingCategoryClass.get(groupPosition).getNameType()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headerAddingCategoryClass.get(groupPosition);
    }

    //Nếu có điều kiện trong trường danh mục chi cha không có danh mục thu con
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (getChildrenCount(groupPosition) == 0) {
            return null;
        } else {
            return this.childAddingCategory.get(this.headerAddingCategoryClass.get(groupPosition).getNameType()).get(childPosition);
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
        tvNameType.setText(headerAddingCategoryClass.get(groupPosition).getNameType());
        ImageView ivIcon = convertView.findViewById(R.id.list_item_icon);
        ivIcon.setImageResource(headerAddingCategoryClass.get(groupPosition).getImageResourceID());

        //Tạo hàm xử lí sự kiện khi click vào danh mục thu cha
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddingActivity.class);
                //Tạo bundle để truyền dữ liệu để chuyển từ AddingTypeActivity sang AddingAcitivty
                Bundle bundle = new Bundle();
                bundle.putInt("addingID", groupPosition);
                //Do khi click vào hàm cha thì ko cần id của thu mục con nên đặt id thư mục con là -1
                bundle.putInt("addingChildID", -1);
                //Gửi dữ liệu để biết thu hay chi
                bundle.putInt("IsType",1);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);

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

        TextView tvNameType = convertView.findViewById(R.id.list_item_text);
        tvNameType.setText(childAddingCategory.get(groupPosition).get(childPosition).getNameType());
        ImageView ivIcon = convertView.findViewById(R.id.list_item_icon);
        ivIcon.setImageResource(childAddingCategory.get(groupPosition).get(childPosition).getImageResourceID());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
