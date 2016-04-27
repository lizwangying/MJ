package com.liz.mj;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.liz.mj.activity.LoginActivity;
import com.liz.mj.view.MySurfaceView;

import java.io.File;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * description: 所有activity的基类
 * author: lizwangying@icloud.com
 * date: 2015-08-20 15:22
 * version: 1.0
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private static Toast mToast;
    private MyHandler baseHandler = new MyHandler();
    protected int layoutId;


    private GestureOverlayView gov;//手写绘图区域
    private Gesture gesture;//手写实例;
    private GestureLibrary gestureLibrary;//创建一个手势仓库
    private String path;//手写文件路径
    private File file;
    private int overlayViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        //初始化Bmob
        Bmob.initialize(this,"3c5885ebb8bb40b1c7b3b3a818d17f7c");
        //手势绘图区域
        if(getOverlayViewId() != 0){//如果有手势需求
            overlayViewId = getOverlayViewId();
            gov = (GestureOverlayView) this.findViewById(overlayViewId);
            initGestureProcess();
        }
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        // 通过注解绑定控件
        ButterKnife.bind(this);
        init(savedInstanceState);

        initView();
        initData();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
    }

    protected void onBeforeSetContentLayout() {
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getOverlayViewId() {
        return overlayViewId;
    }

    public void setOverlayViewId(int overlayViewId) {
        this.overlayViewId = overlayViewId;
    }


    protected View inflateView(int resId) {
        return getLayoutInflater().inflate(resId, null);
    }

    protected void init(Bundle savedInstanceState) {
    }
    public void initView() {
    }
    public void initData() {
    }
    //跳转到Activity
    public void toActivity(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }

    //跳转到Activity 传递参数bundle
    public void toActivity(Class<?> activity, Bundle bundle) {
        Intent i = new Intent(this, activity);
        i.putExtras(bundle);
        startActivity(i);
    }

    //跳转到Activity
    public void toActivity(Class<?> activity, String key, String value) {
        Intent i = new Intent(this, activity);
        i.putExtra(key, value);
        startActivity(i);
    }

    //跳转到Activity
    public void toActivity(Class<?> activity, String key, int value) {
        Intent i = new Intent(this, activity);
        i.putExtra(key, value);
        startActivity(i);
    }

    public void sendMessage(int flag) {
        baseHandler.sendEmptyMessage(flag);
    }

    public void showToast(String toast_message) {
        baseHandler.toastMessage = toast_message;
        sendMessage(MyHandler.SHOW_STR_TOAST);
    }

    public void showToast(int res) {
        baseHandler.toastRes = res;
        sendMessage(MyHandler.SHOW_RES_TOAST);
    }

    private static class MyHandler extends Handler {
        public static final int SHOW_STR_TOAST = 0;
        public static final int SHOW_RES_TOAST = 1;

        private String toastMessage;
        private int toastRes;

        @Override
        public void handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case SHOW_STR_TOAST:
                        if (mToast == null) {
                            mToast = Toast.makeText(App.getContext(), toastMessage, Toast.LENGTH_SHORT);
                        } else {
                            mToast.setText(toastMessage);
                        }
                        mToast.show();
                        break;
                    case SHOW_RES_TOAST:
                        if (mToast == null) {
                            mToast = Toast.makeText(App.getContext(), toastRes, Toast.LENGTH_SHORT);
                        } else {
                            mToast.setText(toastMessage);
                        }
                        mToast.show();
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void initGestureProcess() {
        gov.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_SINGLE);//设置笔画的类型
//		gov.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
        path = new File(Environment.getExternalStorageDirectory(), "gestures").getAbsolutePath();
        //得到默认路径和文件名
        file = new File(path);//实例gesture的文件对象
        gestureLibrary = GestureLibraries.fromFile(file);
        gov.addOnGestureListener(new GestureOverlayView.OnGestureListener() {//绑定手写绘图区域
            @Override
            public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                Log.e("haha", "开始手写啦");
            }

            @Override
            public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
            }

            @Override
            public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                gesture = gestureOverlayView.getGesture();//从绘图区域获得当前的手势
                if (gesture.getStrokesCount() == 1) {//默认一笔画，但是一开始你设置的就是一笔，所以始终为1
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {//判断第某笔画离开屏幕
                        if (gesture.getLength() >= 100) {//判定笔画长度达到100像素
                            addMyGesture("findGesture", gesture);
                        }
                    }
                }

            }

            @Override
            public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
            }
        });

        //----这里是在程序启动的时候进行遍历所有手势!------
        if (!gestureLibrary.load()) {
            showToast("手势仓库加载失败");
        } else {
            Set<String> set = gestureLibrary.getGestureEntries();//取出所有手势
            Object ob[] = set.toArray();
            loadAllGesture(set, ob);
        }
    }



    public void addMyGesture(String name, Gesture gesture) {
        try {
            if (name.equals("findGesture")) {
                findMyGesture(gesture);
            } else {
                if (Environment.getExternalStorageState() != null) {//是否存在sdcard
                    if (!file.exists()) {//是否存在手势
                        //不存在----把我们的手势文件存入
                        gestureLibrary.addGesture(name, gesture);
                        if (gestureLibrary.save()) {//保存到文件中
                            gov.clear(true);//清除笔画
                            gestureToImage(gesture);
                            showToast("手势保存成功");
                            Log.e("haha","手势保存成功");
                        } else {
                            showToast("保存手势失败");
                        }
                    } else {//当存在文件的时候把新的替换旧的，旧的删掉
                        //读取手势文件，得到手势
                        if (!gestureLibrary.load()) {//读取失败
                            showToast("手势读取失败");
                        } else {//读取成功
                            Set<String> set = gestureLibrary.getGestureEntries();//取出所有手势
                            Object obj[] = set.toArray();
                            boolean isHaveGesture = false;
                            for (int i = 0; i < obj.length; i++) {
                                if (obj[i].equals(name)) {
                                    isHaveGesture = true;
                                }
                                if (isHaveGesture) {//替换旧的，旧的删掉
                                    gestureLibrary.removeEntry(name);
//                                    gestureLibrary.removeGesture(name,gesture);
                                    gestureLibrary.addGesture(name, gesture);
                                } else {
                                    gestureLibrary.addGesture(name, gesture);
                                }
                                if (gestureLibrary.save()) {
                                    gov.clear(true);//清除笔画
                                    gestureToImage(gesture);
                                    showToast("手势保存成功");
                                } else {
                                    showToast("手势保存失败");
                                }

                            }

                        }
                    }
                } else {
                    showToast("没有sdcard");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(BaseActivity.this, "操作异常", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadAllGesture(Set<String> set, Object ob[]) {
        if (gestureLibrary.load()) {
            set = gestureLibrary.getGestureEntries();
            ob = set.toArray();
            for (int i = 0; i < ob.length; i++) {
                gestureToImage(gestureLibrary.getGestures((String) ob[i]).get(0));
                MySurfaceView.vector_string.addElement((String) ob[i]);//把手势名字也保存下来
            }
        } else {
            showToast("读取手势失败");
        }


    }

    //手势保存成bitmap
    public void gestureToImage(Gesture gesture) {
        if (MySurfaceView.vector_bitmap != null) {
            MySurfaceView.vector_bitmap.addElement(gesture.toBitmap(100, 100, 12, Color.GREEN));
        }
    }

    public void findMyGesture(Gesture gesture) {
        showToast("find gesture");
        try {
            if (Environment.getExternalStorageState() != null) {//试探终端是否有sdcard
                if ( !file.exists()){//手势文件不存在
                    showToast("手势文件不存在，请重新创建");
                }else{//当存在此文件的时候我们需要删除此手势然后把新的手势放上
                    //读取已经存在的文件，得到文件中的所有手势
                    if (!gestureLibrary.load()){//如果文件读取失败
                        showToast("手势文件读取失败");
                    }else{
                        List<Prediction> predictions = gestureLibrary.recognize(gesture);
                        /*
                        * recognize()返回的是一个prediction集合，包含了所有与gesture匹配的结果
                        * 匹配的结果可能有多个相似的结果
                        * */
                        if (!predictions.isEmpty()){
                            Prediction pre = predictions.get(0);
                            //pre的score代表的相似度。一般不考虑小于1的结果，name为名字
                            if (pre.score>=1){
                                showToast("手势为"+pre.name);
                                if (pre.name.equals("haha")){
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }
                            }
                        }
                    }
                }

            }else {
                showToast("手势匹配失败，没有sd卡");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            showToast("由于出现异常，所以手势匹配异常");
        }
    }
}
