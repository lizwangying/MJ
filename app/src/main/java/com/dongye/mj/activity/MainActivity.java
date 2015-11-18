package com.dongye.mj.activity;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.dongye.mj.BaseActivity;
import com.dongye.mj.R;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.alertdialog)private Button alertDialog;
    private AlertDialog mAlertDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //初始化
        //wish you happy
        //Are you happy?---from happy

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.alertdialog:
                mAlertDialog = new AlertDialog.Builder(MainActivity.this).create();

                break;
        }

    }
}

