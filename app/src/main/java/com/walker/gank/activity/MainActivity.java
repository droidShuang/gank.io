package com.walker.gank.activity;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintToolbar;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.walker.gank.R;
import com.walker.gank.adapter.MyFragmentViewPagerAdapter;
import com.walker.gank.fragment.AboutFragment;
import com.walker.gank.fragment.CategoryFragment;
import com.walker.gank.fragment.DayFragment;
import com.walker.gank.fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.main_bottomBar)
    BottomBar bottomBar;
    @Bind(R.id.main_toolbar)
    TintToolbar toolbar;
    @Bind(R.id.main_content)
    ViewPager content;
    private List<Fragment> fragments;

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        fragments = new ArrayList<>();
        DayFragment dayFragment = new DayFragment();
        CategoryFragment categoryFragment = new CategoryFragment();
        ImageFragment imageFragment = new ImageFragment();
        AboutFragment aboutFragment = new AboutFragment();
        fragments.add(dayFragment);

        fragments.add(imageFragment);
        fragments.add(categoryFragment);
        fragments.add(aboutFragment);
    }

    private void initView() {

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_home:
                        content.setCurrentItem(0);
                        break;
                    case R.id.tab_message:
                        content.setCurrentItem(2);
                        break;
                    case R.id.tab_store:

                        content.setCurrentItem(1);
                        break;
                    case R.id.tab_set:
                        content.setCurrentItem(3);
                        break;
                }
            }
        });
        content.setOffscreenPageLimit(1);
        content.setAdapter(new MyFragmentViewPagerAdapter(getSupportFragmentManager(), fragments));

        //content.setOffscreenPageLimit(4);
        content.addOnPageChangeListener(new MyOnPageChangeListener(bottomBar));

    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        BottomBar bottom;

        public MyOnPageChangeListener(BottomBar bottom) {
            super();
            this.bottom = bottom;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            bottom.selectTabAtPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
