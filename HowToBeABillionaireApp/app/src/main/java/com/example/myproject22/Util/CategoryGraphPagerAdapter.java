package com.example.myproject22.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myproject22.View.Fragment.IncomeCategoryGraphFragment;
import com.example.myproject22.View.Fragment.OutcomeCategoryGraphFragment;

import org.jetbrains.annotations.NotNull;

public class CategoryGraphPagerAdapter extends FragmentPagerAdapter {

    //region Component
    private int id_user;
    private int id_outcome;
    private int id_income;
    //endregion

    public CategoryGraphPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public CategoryGraphPagerAdapter(@NonNull @NotNull FragmentManager fm, int id_user, int id_outcome, int id_income) {
        super(fm);
        this.id_user = id_user;
        this.id_outcome = id_outcome;
        this.id_income = id_income;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IncomeCategoryGraphFragment(this.id_user, this.id_income);
            case 1:
                return new OutcomeCategoryGraphFragment(this.id_user, this.id_outcome);
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tiền thu";
            case 1:
                return "Tiền chi";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
