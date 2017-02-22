package com.walker.gank.Service;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by walker on 2016/12/13 0013.
 */

public interface GitHubService {
    @GET("user/{user}")
    Flowable<String> getGithubUser(@Path("user") String user);

}
