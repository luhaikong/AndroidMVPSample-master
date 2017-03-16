package com.wuxiaolong.androidmvpsample.retrofit;

import com.wuxiaolong.androidmvpsample.mvp.main.MainBean;
import com.wuxiaolong.androidmvpsample.mvp.main.Root;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lhk
 * on 2016/3/24.
 */
public interface ApiStores {
    //baseUrl
    //String API_SERVER_URL = "http://www.weather.com.cn/";

    //加载天气
    @GET("http://www.weather.com.cn/adat/sk/{cityId}.html")
    Observable<MainBean> loadData(@Path("cityId") String cityId);

    //加载天气
    @GET("http://www.weather.com.cn/adat/sk/{cityId}.html")
    Flowable<MainBean> loadFlowableData(@Path("cityId") String cityId);
    
    //另外一个数据接口
    
    String API_SERVER_URL = "http://v.juhe.cn/";
    
    @GET("http://v.juhe.cn/exp/index?key=feec2e995632ec4329ec4591bdd7c20b&com=sf&no=575677355677")
    Observable<Root> loadData();

    @GET("exp/index")
    Observable<Root> loadData(@Query("key") String key
            ,@Query("com") String com
            ,@Query("no") String no);

    @POST("http://v.juhe.cn/exp/index?key=feec2e995632ec4329ec4591bdd7c20b&com=sf&no=575677355677")
    Observable<Root> postData();

    @POST("exp/index")
    Observable<Root> postData(@Query("key") String key
            ,@Query("com") String com
            ,@Query("no") String no);

}
