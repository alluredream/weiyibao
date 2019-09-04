package com.android.test.makestart2.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.android.test.makestart2.LoginToServer;
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
 * Created by AllureDream on 2019-01-07.
 */

public class fankuitoService {
    private String name;
    private String message;
    String urlAddress="http://221.193.236.248:8081/cjhmeframework/maintenance/saveMaintenance.jhtml?";  //写明希望沟通的服务器的地址，此处为本机的Tomcat服务器
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginToServer.class);
        context.startActivity(intent);
    }
    public String doPost(String tagNo, String tagContent,String post,int status,String createUser){  //采用doPost方法发送请求
        try {
            //创建连接
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.addRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setInstanceFollowRedirects(true);
           // connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            // POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.put("tagNo",tagNo);
            obj.put("tagContent",tagContent);
            obj.put("post",post);
            obj.put("status",status);
            obj.put("createUser",createUser);
             out.write(obj.toString().getBytes());
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
            try{
                if(sb!=null){
                    JSONObject jsonObject = JSONObject.parseObject(i);
                    name=jsonObject.getString("resultType");
                    message = jsonObject.getString("resultMsg");
                    MainApplication.getInstance().stringHashMap.put("fankuiresult",name);
                    MainApplication.getInstance().stringHashMap.put("fankuimessage",message);
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
