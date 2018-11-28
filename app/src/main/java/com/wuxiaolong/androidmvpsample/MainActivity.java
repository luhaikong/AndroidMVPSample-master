package com.wuxiaolong.androidmvpsample;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.common.lhk.library.mvp.MvpActivity;
import com.wuxiaolong.androidmvpsample.entity.MainBean;
import com.wuxiaolong.androidmvpsample.mvp.main.IMainView;
import com.wuxiaolong.androidmvpsample.mvp.main.MainPresenter;
import com.wuxiaolong.androidmvpsample.service.UpdateApkService;

public class MainActivity extends MvpActivity<MainPresenter>
        implements IMainView {

    TextView text;
    TextView second;
    ProgressBar mProgressBar;
    Button btnDownload;

    int REQUEST_CODE_UNKNOWN_APP = 100;

    @Override
    protected MainPresenter createPresenter() {
        mvpPresenter = new MainPresenter(this,this);
        return mvpPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = getViewById(R.id.text);
        second = getViewById(R.id.second);
        mProgressBar = getViewById(R.id.mProgressBar);
        btnDownload = getViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownLoadApk("http://220.163.103.24/ydyw/upload/appywhd.apk");
            }
        });
        initPermission();
        //请求接口
        mvpPresenter.loadData("101010100");//昆明：101290101，北京：101010100
    }

    @Override
    protected void doSomeThings() {

    }

    private void startDownLoadApk(String apkurl) {
        if (apkurl != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (hasInstallPermission) {
                    //安装应用
                    downloadAndInstall(apkurl);
                } else {
                    //跳转至“安装未知应用”权限界面，引导用户开启权限
                    Uri selfPackageUri = Uri.parse("package:" + this.getPackageName());
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, selfPackageUri);
                    startActivityForResult(intent, REQUEST_CODE_UNKNOWN_APP);
                }
            } else {
                //安装应用
                downloadAndInstall(apkurl);
            }
        } else {
            Toast.makeText(mBaseContext,"下载地址不正确!!!",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 下载并直接安装
     */
    private void downloadAndInstall(String apkurl){
        Intent intent = new Intent(this, UpdateApkService.class);
        intent.putExtra("data", apkurl);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UNKNOWN_APP) {
            downloadAndInstall("http://220.163.103.24/ydyw/upload/appywhd.apk");
        }
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission_group.STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //需不需要解释的dialog
                if (shouldRequest()) {
                    return;
                }
                //请求权限
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission_group.STORAGE}, 1);
            }
        }
    }

    private boolean shouldRequest() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission_group.STORAGE)) {
            //显示一个对话框，给用户解释
            explainDialog();
            return true;
        }
        return false;
    }

    private void explainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("应用需要获取您的内部存储使用权限,是否授权？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            //请求权限
                            ActivityCompat.requestPermissions(MainActivity.this
                                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                                            , Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    }
                }).setNegativeButton("取消", null)
                .create().show();
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

}
