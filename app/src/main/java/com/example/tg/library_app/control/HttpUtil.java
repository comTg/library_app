package com.example.tg.library_app.control;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.example.tg.library_app.MyApplication;
import com.example.tg.library_app.model.PersistentCookieStore;

/**
 * Created by tg on 2017/4/22 0022.
 */

public class HttpUtil {
    //登录表单网页URL
    public static String LOGINURL = "http://218.87.136.101/museweb/dzjs/login_form.asp";
    //提交登录信息表单
    public static String SUBMITURL = "http://218.87.136.101/museweb/dzjs/login.asp";
    //借还查询表单提交URL
    public static String JHURL = "http://218.87.136.101/museweb/dzjs/jhcx.asp";

    public static String SEARCHBOOK = "http://218.87.136.101/museweb/wxjs/tmjs.asp";
/*    private static OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        //Tip：這裡key必須是String
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }
        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }).build();*/
    private static CookiesManager cookiesManager = new CookiesManager();

    /*
    临时保存cookie的策略
     */
    private static CookieJar cookieJar = new  CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };
    //登录
    public static Response Login(String name, String pw){
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookiesManager)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("user", name)
                .add("pw", pw)
                .build();
        Request request = new Request.Builder()
                .url(SUBMITURL)
                .post(requestBody)
                .build();
        return getResponse(client,request);
    }
    //借还查询
    public static Response Jhcheck(String option){
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookiesManager)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("nCxfs", option)
                .build();
        Request request = new Request.Builder()
                .url(JHURL)
                .post(requestBody)
                .build();
        return getResponse(client,request);
    }
    //续借图书
    public static Response Xjbook(String url){
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookiesManager)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        return getResponse(client,request);
    }
    //临时登录某人的账号，cookie临时保存
    public static Response SearchLogin(String name){
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("user", name)
                .add("pw", name)
                .build();
        Request request = new Request.Builder()
                .url(SUBMITURL)
                .post(requestBody)
                .build();
        return getResponse(client,request);
    }
    //搜索某人借还图书信息，使用临时cookie
    public static Response Searchcheck(String option){
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("nCxfs", option)
                .build();
        Request request = new Request.Builder()
                .url(JHURL)
                .post(requestBody)
                .build();
        return getResponse(client,request);
    }
    /*
    封装发起http请求，返回response
     */
    private static Response getResponse(OkHttpClient client,Request request){
        Response response = null;
        for (int i = 0; i < 30; i++) {
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                break;
            }
        }
        return response;
    }
    /*
    继承CookieJar，永久保存Cookies信息
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }
}


