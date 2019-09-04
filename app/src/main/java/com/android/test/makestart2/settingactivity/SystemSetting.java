package com.android.test.makestart2.settingactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.test.makestart2.R;
import com.android.test.makestart2.Utils.DataCleanManager;
import com.android.test.makestart2.Utils.DataClearUtil;
import com.android.test.makestart2.application.MainApplication;

/**
 * Created by AllureDream on 2018-11-15.
 */

public class SystemSetting extends AppCompatActivity {
    private LinearLayout line1;
    private LinearLayout line2;
    private LinearLayout line3;
    private TextView cacheView;
    private TextView version;
    private SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_setting);
        getSupportActionBar().hide(); //隐藏标题栏
        line3 = (LinearLayout) findViewById(R.id.line3);
        cacheView = (TextView) findViewById(R.id.cache);
        version = (TextView) findViewById(R.id.version);
        sp = this.getSharedPreferences("version", Context.MODE_PRIVATE);

        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);
        findViewById(R.id.toback).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cacheView.setText(DataClearUtil.getTotalCacheSize(this));
        String vstr= (String) version.getText();
        //将版本号存放到sp中
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("oldversion", vstr);
        editor.commit();

        //设置宽度
        setInfo();
     }
    /**
     * 设置宽度
     */
    public void setInfo(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int b =(screenWidth/10)*9;
        int a = (screenWidth/6)*5;

        line1= (LinearLayout) findViewById(R.id.line1);
        line1.setMinimumWidth(b);
        line1.setMinimumHeight(a);
    }

    /**
     * 清除缓存
     * @param view
     */
    public void clear(View view) {
        //清除缓存
        DataClearUtil.cleanAllCache(this);
        Toast.makeText(this, "清除缓存成功", Toast.LENGTH_SHORT).show();
        cacheView.setText(DataClearUtil.getTotalCacheSize(this));

    }

}
