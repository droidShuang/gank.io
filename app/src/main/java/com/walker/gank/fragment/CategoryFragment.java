package com.walker.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.walker.gank.R;
import com.walker.gank.adapter.CategoryViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by walker on 2016/12/20 0020.
 */

public class CategoryFragment extends BaseFragment {
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    CategoryViewPagerAdapter adapter;

    View rootView;
    boolean isPrepared = false;


    public CategoryFragment() {

        super();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.e("onCreateView");
        rootView = View.inflate(container.getContext(), R.layout.fragment_category, null);
        isPrepared = true;
        init();
        return rootView;
    }

    private void init() {
        ButterKnife.bind(this, rootView);
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(CategoryListnFragment.getInstance("Android"));
        fragments.add(CategoryListnFragment.getInstance("iOS"));
        fragments.add(CategoryListnFragment.getInstance("瞎推荐"));
        fragments.add(CategoryListnFragment.getInstance("拓展资源"));
        fragments.add(CategoryListnFragment.getInstance("休息视频"));

        adapter = new CategoryViewPagerAdapter(getChildFragmentManager(), fragments);
        // viewPager.setOffscreenPageLimit(1);
        //viewPager.setCurrentItem(0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.e("isVisibleTouser   " + isVisibleToUser);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!isPrepared || getUserVisibleHint()) {
            Logger.d("CategoryFragment 未加载isPrepared:   " + isPrepared + "     isVisible   " + isVisible);
            return;
        } else {
            Logger.d("CategoryFragment 加载");


        }
    }

    @Override
    public void onDestroyView() {

        adapter = null;
        viewPager = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
