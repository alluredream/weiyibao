package com.android.test.makestart2.application;

import android.app.Application;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by AllureDream on 2018-11-16.
 */

public class MainApplication extends Application {
    private static MainApplication mAPP;
    public HashMap<String,String> stringHashMap = new HashMap<String,String>();

    public  static MainApplication getInstance(){
        return mAPP;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        mAPP = this;
        //setValue(VALUE); // 初始化全局变量
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

    }

}
