package com.yz.account.base.vpadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

public class FragmentViewPagerAdapterAfter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragment;

    public FragmentViewPagerAdapterAfter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragment = mFragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}