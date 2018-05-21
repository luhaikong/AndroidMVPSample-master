package com.common.lhk.library.mvp;

/**
 * Created by MyPC on 2018/4/19.
 * 当不想使用 MVP 模式时继承该基类
 */

public class MvpNActivity extends MvpActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
