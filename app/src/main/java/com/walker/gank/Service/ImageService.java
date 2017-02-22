package com.walker.gank.Service;

import com.walker.gank.bean.ImageInfo;

import io.reactivex.Flowable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * Created by walker on 2016/12/22 0022.
 */

public interface ImageService {
    @FormUrlEncoded
    @GET("imageInfo")
    Flowable<ImageInfo> getImageInfo();

}
