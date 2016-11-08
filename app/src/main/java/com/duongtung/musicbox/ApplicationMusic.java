package com.duongtung.musicbox;

import android.app.Application;

import com.duongtung.musicbox.database.DatabaseHelper;
import com.duongtung.musicbox.utils.SharePreferenceUtils;

/**
 * Created by duong.thanh.tung on 11/8/2016.
 */

public class ApplicationMusic extends Application {
    private static DatabaseHelper data;
    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferenceUtils.getInstance(getApplicationContext());
        data=DatabaseHelper.getInstance(getApplicationContext());
    }

    public static DatabaseHelper getDatabaseHelper(){
        return data;
    }
}
