package com.common.lhk.library.mvp;

/**
 * Created by lhk on 2017/5/26.
 * 返回到主线程的Flowable/Observable数据过滤
 */

public interface ItestinFilter<T> {
    boolean testInfilter(T t);
}
