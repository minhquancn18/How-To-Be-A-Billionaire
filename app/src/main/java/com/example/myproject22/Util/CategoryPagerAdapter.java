package com.example.myproject22.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myproject22.View.IncomeCategoryFragment;
import com.example.myproject22.View.OutcomeCategoryFragment;

public class CategoryPagerAdapter extends FragmentPagerAdapter {
    public CategoryPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IncomeCategoryFragment();
            case 1:
                return new OutcomeCategoryFragment();
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
