package com.example.tg.library_app;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class SingleActivity extends AppCompatActivity {
    //抽象类方法，用于加载不同的Fragment
    protected abstract Fragment createFragment();
    //获取Layout布局的Id
    protected int getLayoutResId(){
        return R.layout.fragment_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

    }
}
