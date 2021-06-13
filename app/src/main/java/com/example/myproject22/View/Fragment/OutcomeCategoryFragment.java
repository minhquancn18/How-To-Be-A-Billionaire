package com.example.myproject22.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myproject22.Model.DayItem;
import com.example.myproject22.R;
import com.example.myproject22.Util.DayItemAdapter;

import java.util.ArrayList;
import java.util.Date;

import io.alterac.blurkit.BlurKit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutcomeCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutcomeCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OutcomeCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OutcomeCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OutcomeCategoryFragment newInstance(String param1, String param2) {
        OutcomeCategoryFragment fragment = new OutcomeCategoryFragment();
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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BlurKit.init(getContext());


        ArrayList<DayItem> days = new ArrayList<>();
        days.add(new DayItem(new Date(), 2));
        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(), 5));


        DayItemAdapter dayItemAdapter = new DayItemAdapter(days, getContext(), false);
        RecyclerView recyclerView = view.findViewById(R.id.day_recycler);
        recyclerView.setAdapter(dayItemAdapter);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outcome_category, container, false);
    }
}