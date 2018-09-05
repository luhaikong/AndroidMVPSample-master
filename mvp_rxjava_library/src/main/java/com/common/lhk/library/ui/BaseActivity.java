package com.common.lhk.library.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    private static final int Storage_Permission = 0x01;
    private static final String[] str_Storage = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public Context mBaseContext;
    public Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseContext = this;
        mActivity = this;
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission(str_Storage);
        }
    }

    public void requestPermission(String[] permissions) {
        for (String s:permissions){
            if (ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED) {
                //询问用户是否拒绝过，如果没有，该方法返回false，如果被拒绝过，该方法返回true
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, s)) {
                    // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限
                    permissionDialogShow(s);
                } else {
                    // 申请授权。
                    ActivityCompat.requestPermissions(this, permissions, Storage_Permission);
                }
            }
        }
    }

    public void permissionDialogShow(final String permission){
        new AlertDialog.Builder(this)
                .setTitle("友好提醒")
                .setMessage("没有存储权限将不能下载文件，请把存储权限赐给我吧！")
                .setPositiveButton("赏你", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // 用户同意继续申请
                        ActivityCompat.requestPermissions(BaseActivity.this, new String[]{permission}, Storage_Permission);
                    }
                })
                .setNegativeButton("不给", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // 用户拒绝申请
                        requestPermission(new String[]{permission});
                    }
                }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Storage_Permission) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
            } else {
                //权限被用户拒绝，做相应事情
                requestPermission(permissions);
            }
        }
    }

    public void toastShow(int resId) {
        Toast.makeText(mBaseContext, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String res) {
        Toast.makeText(mBaseContext, res, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View topView, int resId){
        Snackbar.make(topView,resId,Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(int resId){
        Snackbar.make(getWindow().getDecorView(), resId, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(View topView, String res){
        Snackbar.make(topView,res,Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(String res){
        Snackbar.make(getWindow().getDecorView(), res, Snackbar.LENGTH_SHORT).show();
    }

    public void toastLongShow(int resId) {
        Toast.makeText(mBaseContext, resId, Toast.LENGTH_LONG).show();
    }

    public void toastLongShow(String res) {
        Toast.makeText(mBaseContext, res, Toast.LENGTH_LONG).show();
    }

    public void showLongSnackBar(View topView, int resId){
        Snackbar.make(topView,resId,Snackbar.LENGTH_LONG).show();
    }

    public void showLongSnackBar(int resId){
        Snackbar.make(getWindow().getDecorView(), resId, Snackbar.LENGTH_LONG).show();
    }

    public void showLongSnackBar(View topView, String res){
        Snackbar.make(topView,res,Snackbar.LENGTH_LONG).show();
    }

    public void showLongSnackBar(String res){
        Snackbar.make(getWindow().getDecorView(), res, Snackbar.LENGTH_LONG).show();
    }

    public <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) this.mActivity.findViewById(id);
    }
}
