package com.example.myproject22.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;


import com.example.myproject22.Model.AddingCategoryClass;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Model.IncomeCategoryClass;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Presenter.AddingCategoryAdapter;
import com.example.myproject22.Presenter.IncomeCategoryAdapter;
import com.example.myproject22.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private FloatingActionButton fab_Adding;
    private ArrayList<AddingCategoryClass> moneyType;

    private ArrayList<IncomeCategoryClass> header;
    private HashMap<String ,ArrayList<IncomeCategoryClass>> child;
    private IncomeCategoryAdapter incomeAdapter;

    private SavingDatabaseHelper savingDatabaseHelper;
    private AddingCategoryAdapter addingAdapter;
    private Intent new_intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_category, container, false);

        //Liên kết tới các component bên layout
        lvAddingType = view.findViewById(R.id.explv_addingType);
        fab_Adding = view.findViewById(R.id.fab_adding_category);

        /*new LoadAdding().execute();*/

        header = IncomeCategoryParent();
        child = IncomeHashMap();

        new_intent = getActivity().getIntent();
        incomeAdapter = new IncomeCategoryAdapter(AddingCategoryFragment.this.getContext(), header, child, new_intent);

        lvAddingType.setAdapter(incomeAdapter);

        //Tạo 1 adapter để kết nối giữa layout vào fragment
        /*moneyType = savingDatabaseHelper.getAddingCategoryList();
        Intent new_intent = getActivity().getIntent();
        AddingCategoryAdapter addingAdapter = new AddingCategoryAdapter(view.getContext(), moneyType, new_intent);
        lvAddingType.setAdapter(addingAdapter);*/

        //Tạo event click khi click vào thư mục con sẽ chuyển qua AddingActivity
        lvAddingType.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                PutIntent(v,groupPosition,childPosition);
                return true;
            }
        });

        fab_Adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddingTypeCategoryActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    class LoadAdding extends AsyncTask<Void, Process, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Connect to database to get addingList
            savingDatabaseHelper = new SavingDatabaseHelper(AddingCategoryFragment.this.getContext());
            moneyType = savingDatabaseHelper.getAddingCategoryList();

            header = IncomeCategoryParent();
            child = IncomeHashMap();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            new_intent = getActivity().getIntent();

            //Tạo 1 adapter để kết nối giữa layout vào fragment
            /*addingAdapter = new AddingCategoryAdapter(AddingCategoryFragment.this.getContext(),moneyType,new_intent);*/

            incomeAdapter = new IncomeCategoryAdapter(AddingCategoryFragment.this.getContext(), header, child, new_intent);

            lvAddingType.setAdapter(incomeAdapter);

            return null;
        }
    }

    private void PutIntent(View v,int groupPosition, int childPosition){
        String sMoney="0";
        String sDescription ="";
        new_intent = getActivity().getIntent();
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
        //Do khi click vào thư mục con thì cần id của cả cha lẫn con
        bundle.putInt("addingID", groupPosition);
        bundle.putInt("addingChildID", childPosition);
        //Gửi dữ liệu để biết thu hay chi
        bundle.putInt("IsType",1);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().finish();
    }

    public ArrayList<IncomeCategoryClass> IncomeCategoryParent(){
        ArrayList<IncomeCategoryClass> arrayList = new ArrayList<>();
        ArrayList<IncomeCategoryClass> arrayListTemp = ConnectionClass.getIncomeCategory();

        for(int i = 0; i < arrayListTemp.size(); i++){
            IncomeCategoryClass income = arrayListTemp.get(i);

            String sparent = income.Get_PARENT_NAME();

            if(sparent.equals("0")){
                arrayList.add(income);
            }
        }
        return arrayList;
    }

    public ArrayList<IncomeCategoryClass> IncomeCategoryChild(String name){
        ArrayList<IncomeCategoryClass> arrayList = new ArrayList<>();
        ArrayList<IncomeCategoryClass> arrayListTemp = ConnectionClass.getIncomeCategory();

        for(int i = 0; i < arrayListTemp.size(); i++){
            IncomeCategoryClass income = arrayListTemp.get(i);

            String sparent = income.Get_PARENT_NAME();

            if(sparent.equals(name)){
                arrayList.add(income);
            }
        }
        return arrayList;
    }

    public HashMap<String ,ArrayList<IncomeCategoryClass>> IncomeHashMap(){
        HashMap<String ,ArrayList<IncomeCategoryClass>> hashMap = new HashMap<>();

        ArrayList<IncomeCategoryClass> incomeHeader = IncomeCategoryParent();

        for(int i = 0; i < incomeHeader.size(); i++){
            String name = incomeHeader.get(i).Get_NAME();

            ArrayList<IncomeCategoryClass> incomeChild = IncomeCategoryChild(name);

            hashMap.put(name, incomeChild);
        }

        return hashMap;
    }
}

