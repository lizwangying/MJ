package com.dongye.mj.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * description:一个自定义的透明的AlertDialog
 * author: lizwangying@icloud.com
 * date: 2015-11-16 15:02
 * version:
 */
public class TranslantAlertDialog extends ProgressDialog {
    private Context mContext;

    public TranslantAlertDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    public TranslantAlertDialog(Context context, int theme, Context mContext) {
        super(context, theme);
        this.mContext = mContext;
    }
}

