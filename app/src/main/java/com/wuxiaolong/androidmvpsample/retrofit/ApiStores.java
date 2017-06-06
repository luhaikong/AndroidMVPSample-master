package com.wuxiaolong.androidmvpsample.retrofit;

import com.wuxiaolong.androidmvpsample.entity.MainBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lhk
 * on 2016/3/24.
 */
public interface ApiStores {
    //baseUrl
    String API_SERVER_URL = "http://www.weather.com.cn/";

    //加载天气
    @GET("http://www.weather.com.cn/adat/sk/{cityId}.html")
    Observable<MainBean> loadData(@Path("cityId") String cityId);

    //加载天气
    @GET("http://www.weather.com.cn/adat/sk/{cityId}.html")
    Flowable<MainBean> loadFlowableData(@Path("cityId") String cityId);

}
