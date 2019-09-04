package com.android.test.makestart2.helper;

import com.android.test.makestart2.application.MainApplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by AllureDream on 2018-11-29.
 */

public class StreamTool {
    /**
     * 从输入流中读取数据
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        String serResult= outStream.toString();
        byte[] data = outStream.toByteArray();//网页的二进制数据
        MainApplication.getInstance().stringHashMap.put("serResult",serResult);
        System.out.print("就这是:"+outStream.toString());
        outStream.close();
        inStream.close();
        return data;
    }
}