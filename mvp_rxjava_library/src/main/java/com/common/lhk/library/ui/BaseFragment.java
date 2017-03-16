package com.common.lhk.library.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    public Context mBaseContext;
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBaseContext = getActivity();
    }

    public void toastShow(int resId) {
        Toast.makeText(mBaseContext, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mBaseContext, resId, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View topView, int resId){
        Snackbar.make(topView,resId,Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(int resId){
        Snackbar.make(getActivity().getWindow().getDecorView(), resId, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(View topView, String res){
        Snackbar.make(topView,res,Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(String res){
        Snackbar.make(getActivity().getWindow().getDecorView(), res, Snackbar.LENGTH_SHORT).show();
    }
}
