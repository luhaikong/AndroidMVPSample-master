package com.wuxiaolong.androidmvpsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.common.lhk.library.jsbridge.BridgeHandler;
import com.common.lhk.library.jsbridge.BridgeWebView;
import com.common.lhk.library.jsbridge.CallBackFunction;
import com.common.lhk.library.jsbridge.DefaultHandler;
import com.common.lhk.library.mvp.MvpNActivity;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2019/5/29.
 */

public class WebViewActivity extends MvpNActivity {
    private final static String TAG = WebViewActivity.class.getSimpleName();
    private BridgeWebView mWebView;
    private Button button;
    private EditText et_content;
    private TextView tv_content;

    int RESULT_CODE = 0;

    ValueCallback<Uri> mUploadMessage;

    ValueCallback<Uri[]> mUploadMessageArray;

    static class Location {
        String address;
    }

    static class User {
        String name;
        Location location;
        String testStr;
    }

    private class OnClickBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String data = et_content.getText().toString().trim();
            mWebView.callHandler("functionInJs", data, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    // TODO Auto-generated method stub
                    Log.i(TAG, "reponse data from js " + data);
                    tv_content.setText(data + ": from js");
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        et_content = getViewById(R.id.et_content);
        tv_content = getViewById(R.id.tv_content);
        button = getViewById(R.id.button);
        button.setOnClickListener(new OnClickBtnListener());

        mWebView = getViewById(R.id.webView);
        mWebView.setDefaultHandler(new DefaultHandler());

        mWebView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadMessageArray = filePathCallback;
                pickFile();
                return true;
            }
        });

        mWebView.loadUrl("file:///android_asset/demo.html");

        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                if (function!=null){
                    function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                }
            }
        });

        User user = new User();
        Location location = new Location();
        location.address = "SDU";
        user.location = location;
        user.name = "大头鬼";

        mWebView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                tv_content.setText(data);
            }
        });

//        mWebView.send("hello");
        mWebView.send("hello", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                et_content.setText(data);
            }
        });
    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage && null == mUploadMessageArray){
                return;
            }
            if(null!= mUploadMessage && null == mUploadMessageArray){
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }

            if(null == mUploadMessage && null != mUploadMessageArray){
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessageArray.onReceiveValue(new Uri[]{result});
                mUploadMessageArray = null;
            }

        }
    }

    @Override
    protected void doSomeThings() {

    }
}
