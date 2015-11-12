package com.dongye.mj.network;


/**
 * description: 处理请求返回数据 <br>
 * author: dongyeforever@foxmail.com <br>
 * date: 2015/6/16 19:31 <br>
 * version: 1.0
 */
public interface ResponseListener {
	// 处理数据的
	void onSuccess(String response);

	// 显示错误的
	void onFailure(String error);
}
