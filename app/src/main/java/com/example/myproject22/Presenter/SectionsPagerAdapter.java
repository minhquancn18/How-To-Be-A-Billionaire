package com.example.myproject22.Presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> stringList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position <= getCount()) {
            return stringList.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position <= getCount()) {
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {

        return fragmentList.size();
    }

    public void AddFragment(Fragment fm, String title) {
        fragmentList.add(fm);
        stringList.add(title);
    }
}
