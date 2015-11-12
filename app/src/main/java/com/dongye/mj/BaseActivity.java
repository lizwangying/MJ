package com.dongye.mj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * description: 所有activity的基类
 * author: dongyeforever@foxmail.com
 * date: 2015-08-20 15:22
 * version: 1.0
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
	private static Toast mToast;
	private MyHandler baseHandler = new MyHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
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

	protected void onBeforeSetContentLayout() {}

	protected int getLayoutId() {
		return 0;
	}

	protected View inflateView(int resId) {
		return getLayoutInflater().inflate(resId,null);
	}

	protected void init(Bundle savedInstanceState) {}

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

	public void showToast(String toast_message){
		baseHandler.toastMessage = toast_message;
		sendMessage(MyHandler.SHOW_STR_TOAST);
	}

	public void showToast(int res){
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
						}else{
							mToast.setText(toastMessage);
						}
						mToast.show();
						break;
					case SHOW_RES_TOAST:
						if (mToast == null) {
							mToast = Toast.makeText(App.getContext(), toastRes, Toast.LENGTH_SHORT);
						}else{
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

	public void initView() {

	}

	public void initData() {

	}
}
