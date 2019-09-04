package com.android.test.makestart2.Utils;

public class StringUtils {
    public static boolean isEmpty(String str){
        if(str==null|| "".equals(str)){
            return true;
        }
        return false;
    }
}
