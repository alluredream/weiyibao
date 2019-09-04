package com.android.test.makestart2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.test.makestart2.adapter.MyRecyclerAdapter;
import com.android.test.makestart2.application.MainApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
 import java.util.List;


/**
 * Created by AllureDream on 2018-11-21.
 */

public class noticesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> data;
    private MyRecyclerAdapter recyclerAdapter;
    private WebView webView;
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notices_page);
        //initData();
       // recyclerView = findViewById(R.id.recyclerView);
        //隐藏标题栏
       getSupportActionBar().hide();
        findViewById(R.id.no_toback_a).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         //强制横屏
         setRequestedOrientation(ActivityInfo
                 .SCREEN_ORIENTATION_LANDSCAPE);
        webView = (WebView) findViewById(R.id.openn_otices);
         WebSettings webSettings = webView.getSettings();
         //设置js
         webSettings.setJavaScriptEnabled(true);
         webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
         webView.setWebChromeClient(new WebChromeClient());
        //10.46.15.4:8080
         try {
             String  url = sendPostRequestByForm("http://221.193.236.253:8888/datacenter/index.html");
             //url =  url.replace("\"","");
            webView.loadUrl(url);

          } catch (Exception e) {
             e.printStackTrace();
         }
         webView.setWebViewClient(new WebViewClient(){
             @Override
             public boolean shouldOverrideUrlLoading(WebView view, String url) {
                 view.loadUrl(url);
                 return super.shouldOverrideUrlLoading(view, url);
             }
         });

    }
    private void initData(){
        data = new ArrayList<String>();
        String response = MainApplication.getInstance().stringHashMap.get("i");
        JSONObject jsonObject1 = JSON.parseObject(response);
        String str1 = jsonObject1.getString("resultData");
       JSONObject jsonObject3 = JSON.parseObject(str1);
       JSONArray jsonArray = jsonObject3.getJSONArray("notices");
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject4 = jsonArray.getJSONObject(i);
            String info = jsonObject4.getString("noticeTitle");
         //   String info1 = jsonObject4.getString("title");
            String string =jsonObject4.getString("noticeId");
            String url ="http://221.193.236.248:8081/cjhmeframework/notice/preSysNoticeView.jhtml?noticeId="+string;
            MainApplication.getInstance().stringHashMap.put("noticesUrl",url);
             data.add(info);
        }
    }
    public static String sendPostRequestByForm(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(false);// 是否输入参数
        // byte[] bypes = params.toString().getBytes();
        //  conn.getOutputStream().write(bypes);// 输入参数
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.readLine();
        }else {
            return  "failed";
        }
    }
}
