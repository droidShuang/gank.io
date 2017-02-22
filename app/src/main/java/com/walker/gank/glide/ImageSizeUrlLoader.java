package com.walker.gank.glide;

import android.content.Context;

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * Created by walker on 2016-12-29.
 */

public class ImageSizeUrlLoader extends BaseGlideUrlLoader<ImageSizeModel> {


    public ImageSizeUrlLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(ImageSizeModel model, int width, int height) {
        return model.buildUrl(width, height);
    }


}
