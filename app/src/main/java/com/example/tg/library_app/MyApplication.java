package com.example.tg.library_app;

import android.app.Application;
import android.content.Context;

/**
 * Created by tg on 2017/7/3 0003.
 */
/*
自定义Application类，全局获取context
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
