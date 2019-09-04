package com.android.test.makestart2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.test.makestart2.adapter.MyRecyclerAdapter;
import com.android.test.makestart2.application.MainApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 公告页面首页
 * Created by AllureDream on 2018-11-29.
 */

public class taskInfoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> data;
    private MyRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskinfo_main);
        //隐藏标题栏
        getSupportActionBar().hide();
        findViewById(R.id.no_toback_b).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);
        recyclerView = findViewById(R.id.recyclerView1);

        recyclerAdapter = new MyRecyclerAdapter(taskInfoActivity.this, data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recyclerAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(taskInfoActivity.this,LinearLayoutManager.VERTICAL));
        recyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                 int a = position;
                bundle.putInt("id",a);
                intent.putExtras(bundle);
                intent.setClass(taskInfoActivity.this, taskMainInfoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
                 Intent intent = new Intent();
                Bundle bundle = new Bundle();
                 int a = position;
                bundle.putInt("id",a);
                intent.putExtras(bundle);
                intent.setClass(taskInfoActivity.this, taskMainInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 页面反馈数据
     */
    private void initData(){
        data = new ArrayList<String>();
        String response = MainApplication.getInstance().stringHashMap.get("i");
        JSONObject jsonObject1 = JSON.parseObject(response);
        String str1 = jsonObject1.getString("resultData");
        JSONObject jsonObject2 = JSON.parseObject(str1);
        String str2 = jsonObject2.getString("task");
        JSONObject jsonObject3 = JSON.parseObject(str2);
        JSONArray jsonArray = jsonObject3.getJSONArray("tasks");
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject4 = jsonArray.getJSONObject(i);
            String info = jsonObject4.getString("taskId");
            String info1 = jsonObject4.getString("title");

            String notices2 = jsonObject4.getString("taskUrl");
            MainApplication.getInstance().stringHashMap.put("noticesUrl1",notices2);
              data.add(info1);
        }
    }
}
