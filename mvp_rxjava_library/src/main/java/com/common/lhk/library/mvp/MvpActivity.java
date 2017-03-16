package com.common.lhk.library.mvp;

import android.os.Bundle;

import com.common.lhk.library.ui.BaseActivity;

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {

    public P mvpPresenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
        super.onDestroy();
    }
}
