package com.android.test.makestart2.saoactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.test.makestart2.Entity.Dept;
import com.android.test.makestart2.R;

import com.android.test.makestart2.searchactivity.SearchDaidingActivity;
import com.android.test.makestart2.searchactivity.SearchGongYIActivity;
import com.android.test.makestart2.searchactivity.SearchGongdianActivity;
import com.android.test.makestart2.searchactivity.SearchIOTableActivity;
import com.android.test.makestart2.searchactivity.SearchPitableActivity;
import com.android.test.makestart2.searchactivity.SearchwangluoActivity;
import com.android.test.makestart2.application.MainApplication;
/**
 * Created by AllureDream on 2018-12-03.
 */

public class SearchAfterActivity extends AppCompatActivity{
    private TextView textView;
    private TextView onCode;
    private TextView onDuty;
    private TextView onPer;
    private TextView onTel;
    private TextView onDesc;
    private TextView deptName;
    Dept dept1;
    String content;
    String deptNamee,deptCode,deptTel,deptDescript,parentDeptId;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchafter_info);
        getSupportActionBar().hide(); //隐藏标题栏
        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);
        //返回功能实现
        findViewById(R.id.toOnback).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //添加数据
        //initdata();
        //打开工艺流程图页面
        opengongyi();
    //打开IO表
    openIO();
    initdata1();
    //打开网络拓扑图
    openwltp();
    //打开供电图
    openwgongdian();
    //打开pi图
    openpi();
    //打开关键连锁店一览表
    opendaiding();
    }
    /**
     * 打开工艺页面
     */
    public void opengongyi(){
        findViewById(R.id.opengongyi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchAfterActivity.this,SearchGongYIActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 打开IO表
     */
    public void openIO(){
        findViewById(R.id.iotable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchAfterActivity.this,SearchIOTableActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 打开网络拓扑图表
     */
    public void openwltp(){
        findViewById(R.id.wangluotuopu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchAfterActivity.this,SearchwangluoActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 打开供电图
     */
    public void openwgongdian(){
        findViewById(R.id.gongdian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchAfterActivity.this,SearchGongdianActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 打开Pi图
     */
    public void openpi(){
        findViewById(R.id.pitu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchAfterActivity.this,SearchPitableActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 打开Pi图
     */
    public void opendaiding(){
        findViewById(R.id.daiding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchAfterActivity.this,SearchDaidingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 添加数据
     *    private TextView onCode;
     private TextView onDuty;
     private TextView onPer;
     private TextView onTel;
     private TextView onDesc;
     */
    public void initdata1()  {
        deptName = (TextView) findViewById(R.id.deptName);
        onCode = (TextView) findViewById(R.id.onCode);
        onDuty = (TextView) findViewById(R.id.onDuty);
        onPer  = (TextView) findViewById(R.id.onPer);
        onTel  = (TextView) findViewById(R.id.onTel);
        onDesc = (TextView) findViewById(R.id.onDesc);
        //岗位名称
         Intent intent = getIntent();
        content = intent.getStringExtra(DECODED_CONTENT_KEY);
        MainApplication.getInstance().stringHashMap.put("gangweiname",content);
        SharedPreferences userSettings= getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = userSettings.edit();
        String name = userSettings.getString("dept","");
        //String dept = MainApplication.getInstance().stringHashMap.get("gangweixinxi");
         JSONArray jsonArray = JSON.parseArray(name);
        for(int i=0;i<jsonArray.size();i++){
            String str1 = jsonArray.getString(i);
            JSONObject jsonObject = JSON.parseObject(str1);
            String deptId =  jsonObject.getString("deptId");
            String deptName = jsonObject.getString("deptName");
            String deptCode = jsonObject.getString("deptCode");
            String deptTel = jsonObject.getString("deptTel");
            String deptDescript = jsonObject.getString("deptDescript");
            String parentDeptId = jsonObject.getString("parentDeptId");
            int deptId_i = Integer.parseInt(deptId);
            dept1= new Dept(deptId_i,deptName,deptCode,deptTel,deptDescript,parentDeptId);
            if(dept1.getDeptName().equals(content)){
                deptCode = dept1.getDeptCode();
                onCode.setText(deptCode);
                deptTel = dept1.getDeptTel();
                onTel.setText(deptTel);
                deptDescript = dept1.getDeptDescript();
                onDesc.setText(deptDescript);
                parentDeptId = dept1.getParentDeptId();
                onDuty.setText(parentDeptId);
            }
        }
        deptName.setText(content);
     }
}
