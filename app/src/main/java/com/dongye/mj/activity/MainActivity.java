package com.dongye.mj.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dongye.mj.BaseActivity;
import com.dongye.mj.R;
import com.dongye.mj.view.TranslantAlertDialog;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.alertdialog)
    Button alertDialog;
    private AlertDialog mAlertDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //初始化
        alertDialog.setOnClickListener(this);
        Log.i("haha","what if i don't want to learn");
        //what shoule I do if I lose you

        //what should I do ,I don't want to do anything...
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.alertdialog:
//                mAlertDialog = new AlertDialog.Builder(MainActivity.this).create();
                TranslantAlertDialog.Builder builder = new TranslantAlertDialog.Builder(MainActivity.this);
                builder.setMessage("开心么？");
                builder.setPositiveButton("开心", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Toast.makeText(MainActivity.this, "开心就好", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("不开心", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Toast.makeText(MainActivity.this,"不开心也好",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
                break;
        }

    }
}

