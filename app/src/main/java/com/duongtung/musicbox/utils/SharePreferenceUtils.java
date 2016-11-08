package com.duongtung.musicbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by duong.thanh.tung on 11/8/2016.
 */

public class SharePreferenceUtils implements CommonUtils {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static SharePreferenceUtils instance;

    private SharePreferenceUtils(Context context){
        sharedPreferences  = context.getSharedPreferences(SETTING_ACCOUNT, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static SharePreferenceUtils getInstance(Context context){
        if (instance == null){
            instance = new SharePreferenceUtils(context);
        }
        return instance;
    }

    public static void setSharedPreferences(String tag,String path){
        editor.putString(tag,path);
        editor.commit();
    }

    public static  String getSharedPreferences(String tag){
        return sharedPreferences.getString(tag,null);
    }
}
