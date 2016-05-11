package com.liz.mj.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.liz.mj.BaseActivity;
import com.liz.mj.R;

import butterknife.Bind;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-03-29 20:52
 * version:
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.bg_login)
    ImageView imageBg;
    @Bind(R.id.gaosi)
    TextView gaosi;

    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.layout_login);

    }

    @Override
    public void initView() {
        //高斯模糊，大爷的就是不好用RSIllegalArgumentException: Unsuported element type.
//        applyBlur();
    }

    private void applyBlur() {
        imageBg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //去掉监听事件，因为不需要
                imageBg.getViewTreeObserver().removeOnPreDrawListener(this);
                //制作图片缓存
                imageBg.buildDrawingCache();
                //传给Bitmap
                Bitmap bitmap = imageBg.getDrawingCache();
                //开始高斯模糊
                blur(bitmap,gaosi);
                return true;//我猜测是有个渐进的过程，本来没有模糊，然后模糊的过程，false则不显示
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bitmap, View view){
        float radius = 20;
        Bitmap U8_4Bitmap;
        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888){
            U8_4Bitmap = bitmap;

        }else{
            U8_4Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        }




        //制作一个空的bitmap，大小为text的宽度和高度
        Bitmap overlay = Bitmap.createBitmap(U8_4Bitmap.getWidth(),
                U8_4Bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //通过bitmap保存canvas的状态
        Canvas canvas = new Canvas(overlay);
        //在父布局文件中把Canvas移动到textview的位置
        canvas.translate(-view.getLeft(), -view.getTop());
        //把imageView的内容绘制到bitmap中
        canvas.drawBitmap(bitmap, 0, 0, null);
        //so we get a bitmap the same size as bitmap,It includes a part of ImageView
        //where the TextView back layer content.

        RenderScript rs = RenderScript.create(this);
        //把bitmap复制一份到RenderScript需要的数据中
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        //创建RenderScript，模糊处理的实例
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);
        //设置输入，半径范围
        blur.setRadius(radius);

        blur.forEach(overlayAlloc);
        //把处理后的结果复制到之前的bitmap中
        overlayAlloc.copyTo(overlay);

        view.setBackground(new BitmapDrawable(
                getResources(), overlay));

        rs.destroy();

    }
}
