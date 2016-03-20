package com.dongye.mj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-03-20 17:35
 * version:
 */
public class MySurfaceView extends SurfaceView implements Runnable {
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySurfaceView(Context context) {
        super(context);
    }

    @Override
    public void run() {

    }
}
