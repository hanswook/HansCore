package com.mckj.mc.home.adapter;

import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


public class CommonFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public CommonFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public CommonFragmentAdapter(FragmentManager fm,
                                 List<Fragment> fragment) {
        super(fm);
        mFragments = fragment;
    }

    @Override
    public Fragment getItem(int arg0) {
        Fragment f = mFragments.get(arg0);
        if (f == null) {
        }
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Auto-generated method stub
         super.destroyItem(container, position, object);
    }


}
