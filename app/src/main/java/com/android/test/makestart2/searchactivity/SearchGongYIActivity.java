package com.android.test.makestart2.searchactivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.test.makestart2.R;
import com.android.test.makestart2.application.MainApplication;
import com.android.test.makestart2.settingactivity.fankuiActivity;


/**
 * Created by AllureDream on 2018-12-03.
 */

public class SearchGongYIActivity extends AppCompatActivity {
    private WebView webView;
    private TextView textView;
    private ProgressBar mProgressbar;
    private FrameLayout loadingLayout; //提示用户正在加载数据
    private RelativeLayout webParentView;
    private View mErrorView; //加载错误的视图
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search_gongyi);
       loadingLayout = (FrameLayout) findViewById(R.id.loading_layout);
        initErrorPage();//初始化自定义页面
        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);
            //隐藏标题栏
            getSupportActionBar().hide();
            findViewById(R.id.toOnback).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            //打开页面
            openpage();
        }

        /**
         * 打开页面
         */
    public void openpage(){
        webView = (WebView) findViewById(R.id.gywebview);
        WebSettings webSettings = webView.getSettings();
        //设置js
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //打开反馈信息
        openfab();

         //适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        String deptName = MainApplication.getInstance().stringHashMap.get("gangweiname");
        textView =  (TextView) findViewById(R.id.gongyitu);
        String url ="http://221.193.236.253:8888/岗位";
        String html = "总貌.html";
        String address = url+"/"+deptName.replaceAll("#","%23")+"/"+"工艺流程图"+"/"+html;

        webView.loadUrl(address);
        System.out.println("地址"+address);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //6.0以下执行
                //网络未连接
                showErrorPage();
            }
            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //6.0以上执行
                showErrorPage();//显示错误页面
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingLayout.setVisibility(View.GONE);
            }

        });
        //拿到html的title判断状态
        webView.setWebChromeClient(new WebChromeClient() {
   /*         @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    loadingLayout.setVisibility(View.GONE);
                }
            }*/
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title.contains("404")){
                    showErrorPage();
                }
            }
        });
        webParentView = (RelativeLayout) webView.getParent(); //获取父容器
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        //后退
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }
    /**
     * 打开反馈页面
     */
    public void openfab(){
        findViewById(R.id.open_page).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchGongYIActivity.this, fankuiActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 显示自定义错误提示页面，用一个View覆盖在WebView
     */
    private void showErrorPage() {
        webParentView.removeAllViews(); //移除加载网页错误时，默认的提示信息
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        webParentView.addView(mErrorView, 0, layoutParams); //添加自定义的错误提示的View
    }

    /***
     * 显示加载失败时自定义的网页
     */
    private void initErrorPage() {
        if (mErrorView == null) {
            mErrorView = View.inflate(this, R.layout.layout_load_error, null);
        }
    }
}