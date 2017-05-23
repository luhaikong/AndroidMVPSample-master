package com.wuxiaolong.androidmvpsample;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.lhk.library.mvp.MvpActivity;
import com.wuxiaolong.androidmvpsample.mvp.main.MainBean;
import com.wuxiaolong.androidmvpsample.mvp.main.MainPresenter;
import com.wuxiaolong.androidmvpsample.mvp.main.IMainView;
import com.wuxiaolong.androidmvpsample.mvp.main.Root;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpActivity<MainPresenter>
        implements IMainView {

    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.second)
    TextView second;
    @Bind(R.id.mProgressBar)
    ProgressBar mProgressBar;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //请求接口
        mvpPresenter.loadData("101010100");
        //mvpPresenter.loadSecondData("101290101");
        //mvpPresenter.loadData();
//        mvpPresenter.postData();
    }

    @Override
    public void getDataSuccess(MainBean model) {
        //接口成功回调
        MainBean.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                + getResources().getString(R.string.time) + weatherinfo.getTime();
        text.setText(showData);
    }

    @Override
    public void getDataFail(String msg) {
        toastShow("网络不给力");
    }

    @Override
    public void getSecondSuccess(MainBean model) {
        //接口成功回调
        MainBean.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                + getResources().getString(R.string.time) + weatherinfo.getTime();
        second.setText(showData);
    }

    @Override
    public void getSecondFail(String msg) {
        toastShow(msg);
    }

    @Override
    public void getSecondSuccess(Root model) {
        second.setText(model.toString());
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @OnClick({R.id.text,R.id.second})
    public void onClick(View v){
        
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
