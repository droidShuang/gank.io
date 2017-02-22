package com.walker.gank.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;
import com.walker.gank.R;
import com.walker.gank.glide.ImageSizeModel;
import com.walker.gank.glide.ImageSizeUrlLoader;


/**
 * Created by walker on 2016/12/21 0021.
 */

public class ImageLoader {

    public static void downloadBitmap(Context context, String url) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

            }
        });
    }


    public static void displayImage(ImageSizeModel url, final ImageView iv, final ProgressBar progressBar) {
//        final ObjectAnimator animator = ObjectAnimator.ofInt(iv, "ImageLevel", 0, 100);
//        animator.setDuration(500);
//        animator.setRepeatCount(ObjectAnimator.INFINITE);
//        animator.start();
//        RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(iv.getContext(), R.anim.loading);
//        iv.startAnimation(rotateAnimation);
        Glide.with(iv.getContext()).using(new ImageSizeUrlLoader(iv.getContext())).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.load_error)
                .dontAnimate().skipMemoryCache(true).dontAnimate().fitCenter().listener(new RequestListener<ImageSizeModel, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, ImageSizeModel model, Target<GlideDrawable> target, boolean isFirstResource) {
                //  iv.clearAnimation();
                progressBar.setVisibility(View.GONE);
                Logger.e("图片加载失败");
                e.printStackTrace();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, ImageSizeModel model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                //iv.clearAnimation();
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(iv);
    }

    public static void displayGif(ImageSizeModel url, final ImageView iv, final ProgressBar progressBar) {


//        RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(iv.getContext(), R.anim.loading);
//        iv.startAnimation(rotateAnimation);
        Glide.with(iv.getContext()).using(new ImageSizeUrlLoader(iv.getContext())).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).dontAnimate().fitCenter().error(R.mipmap.load_error)
                .listener(new RequestListener<ImageSizeModel, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, ImageSizeModel model, Target<GifDrawable> target, boolean isFirstResource) {
                        //     iv.clearAnimation();
                        if (e != null) {
                            Logger.e("图片加载失败    " + e.getMessage());
                        }
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, ImageSizeModel model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //   iv.clearAnimation();
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(iv);

    }
}
