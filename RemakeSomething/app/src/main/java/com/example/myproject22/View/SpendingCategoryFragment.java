package com.example.myproject22.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;


import com.example.myproject22.Model.SpendingCategoryClass;
import com.example.myproject22.Presenter.SpendingCategoryAdapter;
import com.example.myproject22.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SpendingCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpendingCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpendingMoneyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpendingCategoryFragment newInstance(String param1, String param2) {
        SpendingCategoryFragment fragment = new SpendingCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Tạo các component trong fragement để kết nối các component của layout
    private ExpandableListView lvSpendingType;

    //Tạo ra 2 list (1 list thư mục cha và 1 list thư mục con) để kết nối tới layout thông qua adapter
    private ArrayList<SpendingCategoryClass> moneyTypeArrayList;
    private HashMap<String, ArrayList<SpendingCategoryClass>> moneyTypeArrayListChild;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spending_category, container, false);

        //Liên kết tới các component bên layout
        lvSpendingType = view.findViewById(R.id.explv_spendingType);

        //Kết nối 2 list với 2 list có sẵn trong hàm AddingType
        moneyTypeArrayListChild = SpendingCategoryClass.getData();
        moneyTypeArrayList = SpendingCategoryClass.spendingTypes;

        //Tạo 1 adapter để kết nối giữa layout vào fragment
        SpendingCategoryAdapter spendingAdapter = new SpendingCategoryAdapter(view.getContext(),
                moneyTypeArrayList, moneyTypeArrayListChild);
        lvSpendingType.setAdapter(spendingAdapter);

        //Tạo event click khi click vào thư mục con sẽ chuyển qua AddingActivity
        lvSpendingType.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(v.getContext(),AddingActivity.class);
                //Tạo bundle để truyền dữ liệu để chuyển từ AddingTypeActivity sang AddingAcitivty
                Bundle bundle = new Bundle();
                //Do khi click vào thư mục con thì cần id của cả cha lẫn con
                bundle.putInt("spendingID", groupPosition);
                bundle.putInt("spendingChildID", childPosition);
                //Gửi dữ liệu để biết thu hay chi
                bundle.putInt("IsType",-1);
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