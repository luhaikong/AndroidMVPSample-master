package com.wuxiaolong.androidmvpsample;

import android.os.Bundle;
import android.widget.ImageView;

import com.common.lhk.library.mvp.BasePresenter;
import com.common.lhk.library.mvp.MvpNActivity;

/**
 * Created by MyPC on 2018/4/19.
 */

public class SplashActivity extends MvpNActivity {

    ImageView iv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv_content = getViewById(R.id.iv_content);
    }

    @Override
    protected void doSomeThings() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
