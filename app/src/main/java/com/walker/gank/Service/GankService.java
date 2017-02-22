package com.walker.gank.Service;

import com.walker.gank.bean.DayGank;
import com.walker.gank.bean.Gank;
import com.walker.gank.bean.HttpResult;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by walker on 2016/12/13 0013.
 */

@SuppressWarnings("SameParameterValue")
public interface GankService {
    @GET("day/{day}")
    Flowable<HttpResult<DayGank>> getGank(@Path("day") String day);

    @GET("data/{category}/10/{pageIndex}")
    Flowable<HttpResult<List<Gank>>> getGankByCategory(@Path("category") String category, @Path("pageIndex") String pageIndex);

    @GET("day/{history}")
    Flowable<HttpResult<List<String>>> getHistoryGank(@Path("history") String history);

    @GET("day/history")
    Flowable<HttpResult<List<String>>> getGankDay();
}
