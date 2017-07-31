package com.wuxiaolong.androidmvpsample;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.common.lhk.library.mvp.MvpActivity;
import com.wuxiaolong.androidmvpsample.mvp.main.IMainView;
import com.wuxiaolong.androidmvpsample.entity.MainBean;
import com.wuxiaolong.androidmvpsample.mvp.main.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpActivity<MainPresenter>
        implements IMainView {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.second)
    TextView second;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    @Override
    protected MainPresenter createPresenter() {
        super.mvpPresenter = new MainPresenter(this,this);
        return mvpPresenter;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //请求接口
        mvpPresenter.loadData("101010100");//昆明：101290101，北京：101010100
    }

    @Override
    public void getDataSuccess(MainBean model) {
        //接口成功回调
        MainBean.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                + getResources().getString(R.string.time) + weatherinfo.getTime();
        text.append(showData);
    }

    @Override
    public void getDataFail(String msg) {
        toastShow("网络不给力");
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void filter(MainBean model) {
        Toast.makeText(mBaseContext,"我正在主线程中过滤一些数据",Toast.LENGTH_LONG).show();
    }

    @Override
    public void doOnNext(MainBean model) {
        Toast.makeText(mBaseContext,"我正在主线程中做一些操作",Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.text,R.id.second})
    public void onClick(View v){
        
    }

}
