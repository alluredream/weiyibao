package com.android.test.makestart2;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AllureDream on 2018-10-09.
 */

public class SplashActivity extends AppCompatActivity  implements View.OnClickListener {

    private int reclen = 5;
    private TextView tv;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //定义全屏参数
        int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag,flag);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        getSupportActionBar().hide(); //隐藏标题栏
        setContentView(R.layout.activity_splash);
        initView();

        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);

        timer.schedule(task,1000,1000);
        handler = new Handler();
        handler.postDelayed(runnable=new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }

    private void initView(){
        tv = findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    reclen--;
                    tv.setText("跳过"+reclen);
                    if(reclen<0){
                        timer.cancel();
                      tv.setVisibility(View.GONE); //倒计时隐藏字体
                    }
                }
            });
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv:
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                if(runnable!=null){
                    handler.removeCallbacks(runnable);
                }
                break;
                default:
                    break;
        }
    }
}
