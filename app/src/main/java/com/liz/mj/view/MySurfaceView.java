package com.liz.mj.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Vector;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-03-20 17:35
 * version:
 */
public class MySurfaceView extends SurfaceView implements Runnable,SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Paint paint;
    private Thread thread;
    public static Vector<Bitmap> vector_bitmap;
    public static Vector<String> vector_string;
    private int col;
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        thread = new Thread(this);
        this.setKeepScreenOn(true);//保持屏幕常亮
        setFocusable(true);
        vector_bitmap = new Vector<>();
        vector_string = new Vector<>();
    }

    @Override
    public void run() {
        while (true) {
            draw();
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        col = this.getWidth()/100;
        thread.start();
    }

    private void draw(){
        try {
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null){
                canvas.drawColor(Color.BLACK);
                if (vector_bitmap != null && vector_bitmap.size() != 0){
                    for (int i = 0; i <vector_bitmap.size() ; i++) {
                        Bitmap bitmap = vector_bitmap.elementAt(i);
                        paint.setStyle(Paint.Style.STROKE);
                        canvas.drawRect((i % col) * 104 + 1, (i / col) * 100 + 1, (i % col) * 104 + 104 - 2, (i / col) * 100 + 100 - 2, paint);
                        canvas.drawBitmap(bitmap, (i % col) * 100, (i / col) * 100, paint);
                        paint.setColor(Color.YELLOW);
                        canvas.drawText(vector_string.elementAt(i), (i % col) * 104 + 10, (i / col) * 100 + 97, paint);
                        paint.setColor(Color.WHITE);
                    }
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
