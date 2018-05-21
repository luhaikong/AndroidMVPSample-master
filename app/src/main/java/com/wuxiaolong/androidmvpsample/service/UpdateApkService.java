package com.wuxiaolong.androidmvpsample.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.wuxiaolong.androidmvpsample.R;

/**
 * Created by lhk on 2016/3/3.
 * AndroidManifest.xml配置方法：
 * <service
 *      android:name=".service.UpdateApkService"
 *      android:enabled="true"/>
 *
 * 使用方法：
 * private void startDownLoadApk(String apkurl) {
        if (apkurl != null) {
            Intent intent = new Intent(this, UpdateApkService.class);
            intent.putExtra("data", apkurl);
            startService(intent);
        } else {
            Toast.makeText(mContext,"下载地址不正确!!!",Toast.LENGTH_LONG).show();
        }
    }
 */
public class UpdateApkService extends Service {

    /** 安卓系统下载类 **/
    DownloadManager manager;

    /** 接收下载完的广播 **/
    DownloadCompleteReceiver receiver;

    /** 初始化下载器 **/
    private void initDownManager(String strUrl) {

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        receiver = new DownloadCompleteReceiver();

        //设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(strUrl));

        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);

        // 下载时，通知栏显示途中
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        down.setTitle(getResources().getString(R.string.app_name));
        down.setDescription("下载进度:");

        // 显示下载界面
        down.setVisibleInDownloadsUi(true);

        // 设置下载后文件存放的位置
        down.setDestinationInExternalFilesDir(this,
                Environment.DIRECTORY_DOWNLOADS, "ynyx_vehicle.apk");

        // 将下载请求放入队列
        manager.enqueue(down);

        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent!=null){
            String data = intent.getStringExtra("data");
            if (data!=null){
                // 调用下载
                try{
                    initDownManager(data);
                }catch (Exception e){
                    e.printStackTrace();
                    try {
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent intent0 = new Intent(Intent.ACTION_VIEW, uri);
                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent0);
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(),"下载失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // 注销下载广播
        if (receiver != null){
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //判断是否下载完成的广播
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

                //获取下载的文件id
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                //自动安装apk
                installAPK(manager.getUriForDownloadedFile(downId));

                //停止服务并关闭广播
                UpdateApkService.this.stopSelf();

            }
            Log.d("UpdateApkService","downLoad_End");
        }

        /**
         * 安装apk文件
         */
        private void installAPK(Uri apk) {

            // 通过Intent安装APK文件
            Intent intents = new Intent();

            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
            intents.setType("application/vnd.android.package-archive");
            intents.setData(apk);
            intents.setDataAndType(apk,"application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //android.os.Process.killProcess(android.os.Process.myPid());
            // 如果不加上这句的话在apk安装完成之后点击打开会崩溃

            startActivity(intents);
        }

    }
}