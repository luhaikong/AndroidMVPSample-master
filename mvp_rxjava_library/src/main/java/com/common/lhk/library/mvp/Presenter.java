package com.common.lhk.library.mvp;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
