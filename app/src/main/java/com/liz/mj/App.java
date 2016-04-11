package com.liz.mj;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.liz.mj.network.OkHttpStack;
import com.liz.mj.util.ImagePipelineConfigFactory;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.okhttp.OkHttpClient;

public class App extends Application {
	private static App instance;
	private static Context context;
	/**
	 * Log or request TAG
	 */
	public static final String TAG = App.class.getSimpleName();
	/**
	 * Global request queue for Volley
	 */
	private RequestQueue mRequestQueue;
	private OkHttpClient okHttpClient;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		instance = this;
		Fresco.initialize(this, ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this));

	}

	public static synchronized App getInstance() {
		return instance;
	}

	/*
	 * 获取到全局的context
	 */
	public static Context getContext() {
		return context;
	}

	public OkHttpClient getOkHttpClient() {
		if (okHttpClient == null) {
			okHttpClient = new OkHttpClient();
		}
		return okHttpClient;
	}

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		synchronized (App.class) {
			if (mRequestQueue == null) {
//				client.networkInterceptors().add(new StethoInterceptor());
				mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttpStack(getOkHttpClient()));//使用okHttp
//				mRequestQueue = Volley.newRequestQueue(getApplicationContext()); //使用volley默认
			}
		}
		return mRequestQueue;
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified then
	 * it is used else Default TAG is used.
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		VolleyLog.d("Adding request to queue: %s", req.getUrl());
		getRequestQueue().add(req);
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 *
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
