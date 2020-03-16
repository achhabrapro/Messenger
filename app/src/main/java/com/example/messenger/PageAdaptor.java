package com.example.messenger;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdaptor extends FragmentPagerAdapter {

    private int num_of_tabs;

    public PageAdaptor(FragmentManager fm,int num_of_tabs) {
        super(fm);
        this.num_of_tabs=num_of_tabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:return new ChatFragment();
            case 1:return new ContactsFragment();
            default:return null;
        }
    }
    @Override
    public int getCount() {
        return num_of_tabs;
    }
}
