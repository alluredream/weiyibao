package com.android.test.makestart2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
 import com.alibaba.fastjson.JSONObject;
import com.android.test.makestart2.application.MainApplication;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;


/**
 * Created by AllureDream on 2018-10-11.
 */

public class LoginToServer extends AppCompatActivity{
     private String name1 ;
            String urlAddress="http://221.193.236.248:8081/cjhmeframework/system/login.jhtml?";  //服务器的地址

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginToServer.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public String doPost(String name, String password){  //采用doPost方法发送请求
        try {
            //创建连接
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            // POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.put("userName", name);
            obj.put("password", password);
            out.writeBytes(obj.toString());
            out.flush();
            out.close();
            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String i;
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = URLDecoder.decode(lines, "utf-8");
                sb.append(lines);
            }
            i=sb.toString();
            MainApplication.getInstance().stringHashMap.put("i",i);
            MainApplication.getInstance().stringHashMap.put("fornumber",i);
           try{
               if(sb!=null){
                   JSONObject jsonObject = JSONObject.parseObject(i);
                   name1=jsonObject.getString("resultType");
                    MainApplication.getInstance().stringHashMap.put("tag",name1);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
