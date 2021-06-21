package com.example.myproject22.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myproject22.View.Fragment.IncomeCategoryGraphFragment;
import com.example.myproject22.View.Fragment.OutcomeCategoryGraphFragment;

public class CategoryGraphPagerAdapter extends FragmentPagerAdapter {
    int a;
    int b;
    public CategoryGraphPagerAdapter(@NonNull FragmentManager fm, int a, int b) {

        super(fm);
        this.a = a;
        this.b = b;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IncomeCategoryGraphFragment(a, b);
            case 1:
                return new OutcomeCategoryGraphFragment();
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
