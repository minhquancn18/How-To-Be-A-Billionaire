package com.example.myproject22.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import androidx.fragment.app.Fragment;


import com.example.myproject22.Model.AddingCategoryClass;
import com.example.myproject22.Presenter.AddingCategoryAdapter;
import com.example.myproject22.R;
import com.example.myproject22.View.Activity.AddingActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddingCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddingCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddingMoneyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddingCategoryFragment newInstance(String param1, String param2) {
        AddingCategoryFragment fragment = new AddingCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Tạo các component trong fragement để kết nối các component của layout
    private ExpandableListView lvAddingType;

    //Tạo ra 2 list (1 list thư mục cha và 1 list thư mục con) để kết nối tới layout thông qua adapter
    private ArrayList<AddingCategoryClass> moneyTypeArrayList;
    private HashMap<String, ArrayList<AddingCategoryClass>> moneyTypeArrayListChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_category, container, false);

        //Liên kết tới các component bên layout
        lvAddingType = view.findViewById(R.id.explv_addingType);

        //Kết nối 2 list với 2 list có sẵn trong hàm AddingType
        moneyTypeArrayList = AddingCategoryClass.addingtypes;
        moneyTypeArrayListChild = AddingCategoryClass.getData();

        //Tạo 1 adapter để kết nối giữa layout vào fragment
        AddingCategoryAdapter addingAdapter = new AddingCategoryAdapter(view.getContext(),
                moneyTypeArrayList,
                moneyTypeArrayListChild);
        lvAddingType.setAdapter(addingAdapter);

        //Tạo event click khi click vào thư mục con sẽ chuyển qua AddingActivity
        lvAddingType.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(v.getContext(), AddingActivity.class);
                //Tạo bundle để truyền dữ liệu để chuyển từ AddingTypeActivity sang AddingAcitivty
                Bundle bundle = new Bundle();
                //Do khi click vào thư mục con thì cần id của cả cha lẫn con
                bundle.putInt("addingID", groupPosition);
                bundle.putInt("addingChildID", childPosition);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
                return true;
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}