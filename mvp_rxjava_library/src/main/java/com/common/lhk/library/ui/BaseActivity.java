package com.common.lhk.library.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    public Context mBaseContext;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mBaseContext = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mBaseContext = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mBaseContext = this;
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
}
