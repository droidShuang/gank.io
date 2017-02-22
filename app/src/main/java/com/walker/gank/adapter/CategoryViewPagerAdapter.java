package com.walker.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by walker on 2016/12/26 0026.
 */

public class CategoryViewPagerAdapter extends FragmentPagerAdapter {
    final List<Fragment> fragments;
    final FragmentTransaction fragmentTransaction;

    public CategoryViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        fragmentTransaction = fm.beginTransaction();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Android";
            case 1:
                return "iOS";
            case 2:
                return "瞎推荐";
            case 3:
                return "拓展资源";
            case 4:
                return "休息视频";
            default:
                return "";

        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

    }
}
