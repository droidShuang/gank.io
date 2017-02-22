package com.walker.gank.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.walker.gank.R;
import com.walker.gank.adapter.ImageViewPageAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageViewPagerActivity extends AppCompatActivity {
    @Bind(R.id.image_tx_index)
    TextView txIndex;
    @Bind(R.id.image_vp_container)
    ViewPager viewPager;
    ImageViewPageAdapter adapter;
    ArrayList<String> urls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_pager);
        ButterKnife.bind(this);
        init();

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);

        super.onDestroy();
    }

    private void init() {
        initData();
        initView();
    }

    private void initView() {
        adapter = new ImageViewPageAdapter(urls);
        viewPager.addOnPageChangeListener(new ViewPagerChangeListner());
        viewPager.setAdapter(adapter);
        txIndex.setText("1/" + urls.size());

    }

    private void initData() {
        urls = getIntent().getStringArrayListExtra("imageUrls");

    }

    private class ViewPagerChangeListner implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            txIndex.setText((position + 1) + "/" + urls.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
