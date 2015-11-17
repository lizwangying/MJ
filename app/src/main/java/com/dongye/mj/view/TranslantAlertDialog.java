package com.dongye.mj.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.dongye.mj.R;

/**
 * description:一个自定义的透明的AlertDialog
 * author: lizwangying@icloud.com
 * date: 2015-11-16 15:02
 * version:
 */
public class TranslantAlertDialog extends Dialog {
    private Context mContext;

    public TranslantAlertDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    public TranslantAlertDialog(Context context, int theme, Context mContext) {
        super(context, theme);
        this.mContext = mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertdialog_diy);
    }


    
}

