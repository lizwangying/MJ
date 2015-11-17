package com.dongye.mj.activity;

import android.os.Bundle;
import android.view.View;

import com.dongye.mj.BaseActivity;
import com.dongye.mj.R;

import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.alertdialog)
    void popAlert() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //初始化

    }

    @Override
    public void onClick(View view) {

    }
    //点击事件写这里
}

