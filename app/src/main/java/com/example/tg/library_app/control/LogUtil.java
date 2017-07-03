package com.example.tg.library_app.control;

import android.util.Log;

/**
 * Created by tg on 2017/7/3 0003.
 */

public class LogUtil {
    private static final int VERBOUS = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;
    public static int level = VERBOUS;

    public static void v(String tag,String msg){
        if(level<=VERBOUS){
            Log.v(tag,msg);
        }
    }
    public static void d(String tag,String msg){
        if(level<=VERBOUS){
            Log.d(tag,msg);
        }
    }
    public static void i(String tag,String msg){
        if(level<=VERBOUS){
            Log.i(tag,msg);
        }
    }
    public static void w(String tag,String msg){
        if(level<=VERBOUS){
            Log.w(tag,msg);
        }
    }
    public static void e(String tag,String msg){
        if(level<=VERBOUS){
            Log.e(tag,msg);
        }
    }
}
