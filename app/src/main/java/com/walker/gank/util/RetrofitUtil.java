package com.walker.gank.util;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.walker.gank.Service.GankService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by walker on 2016/12/22 0022.
 */

public class RetrofitUtil {

    public static class RetrofitHolder {
        private static GankService gankService = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GankService.class);


    }

    public static GankService getGankService() {
        return RetrofitHolder.gankService;
    }


}
