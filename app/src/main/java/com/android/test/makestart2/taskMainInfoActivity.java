package com.android.test.makestart2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.android.test.makestart2.application.MainApplication;

/**
 * 公共详情页面
 * Created by AllureDream on 2018-11-29.
 */

public class taskMainInfoActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_info);
        getSupportActionBar().hide(); //隐藏标题栏
        findViewById(R.id.noinfo_toback).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.notices_webview);
        String response = MainApplication.getInstance().stringHashMap.get("noticesUrl1");
         webView.loadUrl(response);
    }
}
