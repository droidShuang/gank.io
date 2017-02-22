package com.walker.gank.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.walker.gank.R;
import com.walker.gank.bean.ImageInfo;
import com.walker.gank.glide.ImageSizeModel;
import com.walker.gank.glide.ImageSizeModelImp;
import com.walker.gank.util.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;
import uk.co.senab.photoview.PhotoView;


/**
 * Created by walker on 2016/12/21 0021.
 */

public class ImageViewPageAdapter extends PagerAdapter {
    private ArrayList<String> urls;


    public ImageViewPageAdapter(ArrayList<String> urls) {
        super();
        this.urls = urls;

    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_big_image, container, false);
        final PhotoView photoView = (PhotoView) itemView.findViewById(R.id.item_pv);
        final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.item_load_progress);


        OkHttpUtils.get().url(urls.get(position) + "?imageInfo").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Logger.json(response);
                ImageInfo imageInfo = new Gson().fromJson(response, ImageInfo.class);
                //        + "/h/" + DensityUtils.dip2px(context, 100)
                Logger.d("width " + imageInfo.getWidth() + " height " + imageInfo.getHeight());

                photoView.setMaxHeight(imageInfo.getHeight() / 2);
                photoView.setMaxWidth(imageInfo.getWidth() / 2);
                ImageSizeModel imageSizeModel = new ImageSizeModelImp(urls.get(position));
                if (imageInfo.getFormat().contains("gif")) {

                    ImageLoader.displayGif(imageSizeModel, photoView, progressBar);


                } else {

                    ImageLoader.displayImage(imageSizeModel, photoView, progressBar);
                }
            }
        });
        //      ImageLoader.displayImage(urls.get(position), photoView);
        container.addView(photoView);

        //   photoView.setScaleType(ImageView.ScaleType.CENTER);


        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((PhotoView) object).setImageDrawable(null);
        ((PhotoView) object).setImageBitmap(null);
        container.removeView((View) object);
    }
}
