package com.example.cook;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shubin on 2016/9/14.
 */
public class Cook extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
