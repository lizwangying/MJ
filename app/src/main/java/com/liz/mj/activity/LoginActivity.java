package com.liz.mj.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liz.mj.BaseActivity;
import com.liz.mj.R;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-03-29 20:52
 * version:
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.bg_login)
    ImageView imageBg;
    //    @Bind(R.id.gaosi)
//    TextView gaosi;
    @Bind(R.id.email)
    EditText mUserName;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.email_sign_in_button)
    Button buttonLogin;
    @Bind(R.id.text_register)
    TextView textViewRegister;
    @Bind(R.id.text_not_register)
    TextView textViewNotRegister;

    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.layout_login);

    }

    @Override
    public void initView() {
        Spannable registerText = new SpannableString(textViewRegister.getText());
        registerText.setSpan(new UnderlineSpan(), 0, textViewRegister.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewRegister.setText(registerText);
        Spannable notRegisterText = new SpannableString(textViewNotRegister.getText());
        registerText.setSpan(new UnderlineSpan(), 0, textViewNotRegister.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewNotRegister.setText(notRegisterText);
        BmobUser user = BmobUser.getCurrentUser(LoginActivity.this);
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        //高斯模糊，大爷的就是不好用RSIllegalArgumentException: Unsuported element type.
//        applyBlur();
    }

    @OnClick({R.id.text_not_register, R.id.text_register, R.id.email_sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_sign_in_button:
                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(mUserName.getText().toString())) {
                    mUserName.setError("用户名不能为空");
                    break;
                }
                if (TextUtils.isEmpty(mPasswordView.getText().toString())) {
                    mPasswordView.setError("密码不能为空");
                    break;
                }
                BmobUser user = new BmobUser();
                user.setUsername(mUserName.getText().toString());
                user.setPassword(mPasswordView.getText().toString());
                user.login(LoginActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(LoginActivity.this, "登录失败,估计是你没网", Toast.LENGTH_SHORT).show();

                    }
                });
                break;

            case R.id.text_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.text_not_register:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }

    //    private void applyBlur() {
//        imageBg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                //去掉监听事件，因为不需要
//                imageBg.getViewTreeObserver().removeOnPreDrawListener(this);
//                //制作图片缓存
//                imageBg.buildDrawingCache();
//                //传给Bitmap
//                Bitmap bitmap = imageBg.getDrawingCache();
//                //开始高斯模糊
//                blur(bitmap,gaosi);
//                return true;//我猜测是有个渐进的过程，本来没有模糊，然后模糊的过程，false则不显示
//            }
//        });
//    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bitmap, View view) {
        float radius = 20;
        Bitmap U8_4Bitmap;
        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888) {
            U8_4Bitmap = bitmap;

        } else {
            U8_4Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
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
