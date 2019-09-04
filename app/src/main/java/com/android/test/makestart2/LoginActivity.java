package com.android.test.makestart2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
 import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


 import com.android.test.makestart2.application.MainApplication;
import com.android.test.makestart2.helper.Http;


import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by AllureDream on 2018-11-02.
 */

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText name;
    private EditText password;
    private CheckBox rem_pw, auto_login;
    private String userNameValue,passwordValue;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login);

        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        name = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pwd);
        rem_pw = (CheckBox) findViewById(R.id.cb_mima);
        auto_login = (CheckBox) findViewById(R.id.cb_auto);

        //隐藏标题栏
        getSupportActionBar().hide();
        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);

        login = (Button) findViewById(R.id.buttonLogin);
        name =(EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pwd);
        //判断记住密码多选框的状态
        if(sp.getBoolean("ISCHECK", false))
        {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            name.setText(sp.getString("USER_NAME", ""));
            password.setText(sp.getString("PASSWORD", ""));


            //判断自动登陆多选框状态
            if(sp.getBoolean("AUTO_ISCHECK", false))
            {
                //设置默认是自动登录状态
                auto_login.setChecked(true);
                boolean b = false;
                try {
                    b = checkUser(name.getText().toString(),password.getText().toString());
                    MainApplication.getInstance().stringHashMap.put("useradmin",name.getText().toString());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //跳转界面
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameValue = name.getText().toString();
                passwordValue = password.getText().toString();
                boolean b = false;
                try {
                    b = checkUser(name.getText().toString(),password.getText().toString());
                    MainApplication.getInstance().stringHashMap.put("useradmin",name.getText().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
             String str = MainApplication.getInstance().stringHashMap.get("tag");
              if(str.equals("success")){
                  if(rem_pw.isChecked())
                  {
                      //记住用户名、密码、
                      SharedPreferences.Editor editor = sp.edit();
                      editor.putString("USER_NAME", userNameValue);
                      editor.putString("PASSWORD",passwordValue);
                      editor.commit();
                  }
                  String dept =  Http.getUrlData("http://221.193.236.253:8888/wyb/dept/getAllDept.jhtml");
                  SharedPreferences.Editor editor = sp.edit();
                  editor.putString("dept", dept);
                  editor.commit();
               //   MainApplication.getInstance().stringHashMap.put("gangweixinxi",dept);
                  Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                   startActivity(intent);
               }else {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (rem_pw.isChecked()) {
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();
                }else {
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }
            }
        });

        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
                } else {
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }
    public boolean checkUser(String name, String password) throws IOException, JSONException {
        LoginToServer httpClientToServer = new LoginToServer();
        String response = httpClientToServer.doPost(name,password);
        if("true".equals(response)){
            return true;
        }else{
            return false;
        }
    }
}
