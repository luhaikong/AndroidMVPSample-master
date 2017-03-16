package com.common.lhk.library.mvp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.common.lhk.library.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {

    public P mvpPresenter;
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
        super.onDestroyView();
    }
}
