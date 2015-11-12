package com.dongye.mj.network;


import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.dongye.mj.util.LogUtil;

public class GetNetError {

	public static String getNetError(VolleyError error) {
		String errorType;
		if (error instanceof NoConnectionError) {
			errorType = "无网络连接";
		} else if (error instanceof TimeoutError) {
			errorType = "当前网络环境较差";
		} else if (error instanceof NetworkError) {
			errorType = "网络异常";
		} else if (error instanceof ServerError) {
			errorType = "服务器异常";
		} else {
			errorType = "其他错误";
		}
		LogUtil.e(errorType);
		return errorType;
	}
}