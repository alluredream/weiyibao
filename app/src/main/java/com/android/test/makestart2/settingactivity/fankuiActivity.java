package com.android.test.makestart2.settingactivity;

 import android.content.pm.ActivityInfo;
 import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
 import android.util.DisplayMetrics;
 import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 import android.widget.GridLayout;
 import android.widget.Toast;

import com.android.test.makestart2.R;
import com.android.test.makestart2.application.MainApplication;
import com.android.test.makestart2.service.fankuitoService;


/**
 * Created by AllureDream on 2018-12-24.
 */

public class fankuiActivity extends AppCompatActivity{
    private EditText edit_name;
    private EditText edit_all20;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.fankui_main);
        //隐藏标题栏
        getSupportActionBar().hide();
        findViewById(R.id.toback).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_all20=  (EditText) findViewById(R.id.edit_all20);
        button = (Button) findViewById(R.id.submit_info);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = false;
                String createUser = MainApplication.getInstance().stringHashMap.get("useradmin");
                String deptName = MainApplication.getInstance().stringHashMap.get("deptName");
                b =  checkFankui(edit_name.getText().toString(),edit_all20.getText().toString(),deptName,1,createUser);
                String str = MainApplication.getInstance().stringHashMap.get("fankuiresult");
                if(str.equals("success")){
                    String message = MainApplication.getInstance().stringHashMap.get("fankuimessage");
                    Toast.makeText(fankuiActivity.this,message,Toast.LENGTH_SHORT).show();
                }else {
                    String message = MainApplication.getInstance().stringHashMap.get("fankuimessage");
                    Toast.makeText(fankuiActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            }
        });

        //设置宽度
        setwight();
    }

    /**
     * 添加
     * @param tagNo
     * @param tagContent
     * @param post
     * @param status
     * @param createUser
     * @return
     */
    public boolean checkFankui(String tagNo,String tagContent,String post,int status,String createUser){
        fankuitoService  fankuitoService = new fankuitoService();
        String response= fankuitoService.doPost(tagNo,tagContent,post,status,createUser);
        if("true".equals(response)){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 设置宽度
     */
    public void setwight(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int a = (screenWidth/6)*5;
        edit_name.setWidth(a);
        edit_all20.setWidth(a);
    }
}
