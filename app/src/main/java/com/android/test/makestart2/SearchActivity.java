package com.android.test.makestart2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.test.makestart2.Entity.Dept;
import com.android.test.makestart2.Utils.StringUtils;
import com.android.test.makestart2.application.MainApplication;
import com.android.test.makestart2.helper.Http;
import com.android.test.makestart2.helper.RecordSQLiteOpenHelper;
import com.android.test.makestart2.helper.StreamTool;
import com.android.test.makestart2.saoactivity.SearchAfterActivity;
import com.android.test.makestart2.searchactivity.SearchDaidingActivity;
import com.android.test.makestart2.view.MyListView;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private EditText et_search;
    private TextView tv_tip;
    private MyListView listView;
    private TextView tv_clear;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    Map<Integer,Dept> map;
    AutoCompleteTextView textView;
    public static  String[] CONTENTS ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_main);
        //清空数据
        deleteData();
        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);
        // 初始化控件
        initView();
        textView = (AutoCompleteTextView) findViewById(R.id.et_search);
        getdeptname();
        //创建一个ArrayAdapter适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,CONTENTS);
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position).toString();
                boolean hasData = hasData(textView.getText().toString().trim());
                if (!hasData) {
                    insertData(textView.getText().toString().trim());
                    queryData("");
                }
                String name = textView.getText().toString().trim();
                Intent intent = new Intent(SearchActivity.this,SearchAfterActivity.class);
                intent.putExtra("codedContent", name);
                setResult(RESULT_OK, intent);
                 startActivity(intent);
            }
        });
        //添加数据
        add();
        getSupportActionBar().hide(); //隐藏标题栏
        findViewById(R.id.toback).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // 清空搜索历史
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });
        //点击搜索历史，跳转页面查询
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                 // TODO   MainApplication.getInstance().stringHashMap.put("deptName",name);
                 // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询
                Intent intent = new Intent(SearchActivity.this,SearchAfterActivity.class);
                intent.putExtra("codedContent", name);
                setResult(RESULT_OK, intent);
                startActivity(intent);

            }
        });
        // 第一次进入查询所有的历史记录
        queryData("");
    }
    /**
     * 查询数据添加
     */
    public void add(){
        db =  helper.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        String name = textView.getText().toString();
              values1.put("name", name);
    }
    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }
    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }
    /**
     * 调整
     */
    private void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView = (com.android.test.makestart2.view.MyListView) findViewById(R.id.listView);
        tv_clear = (TextView) findViewById(R.id.tv_clear);

        // 调整EditText左边的搜索按钮的大小
        Drawable drawable = getResources().getDrawable(R.drawable.search);
        drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
    }
    /**
     * 拿到数据
     *
     */
    public void getdeptname(){
        SharedPreferences userSettings= getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = userSettings.edit();
        String name = userSettings.getString("dept","");
       // String dept = MainApplication.getInstance().stringHashMap.get("gangweixinxi");
        JSONArray jsonArray = JSON.parseArray(name);
        CONTENTS = new String[jsonArray.size()];
        map  = new HashMap<>(0);
         for(int i=0;i<jsonArray.size();i++){
             String str1 = jsonArray.getString(i);
             JSONObject jsonObject = JSON.parseObject(str1);
              String deptName = jsonObject.getString("deptName");
            CONTENTS[i] =deptName;
        }
    }
}
