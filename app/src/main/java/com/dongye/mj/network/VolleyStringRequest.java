package com.dongye.mj.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dongye.mj.App;
import com.dongye.mj.util.LogUtil;

import java.util.Map;

/**
 * description: Volley网络请求工具类 <br>
 * author: dongyeforever@foxmail.com <br>
 * date: 2015/6/16 19:54 <br>
 * version: 1.0
 */
public class VolleyStringRequest {
	private Map<String,String> params;
	private ResponseListener listener;

	/**
	 * description: GET请求 <br>
	 * author: dongyeforever@foxmail.com <br>
	 * @param params 请求参数
	 * @param listener 返回数据处理
	 * date: 2015/6/16 20:08 <br>
	 * version: 1.0
	 */
	public void get(String url, Map<String, String> params, ResponseListener listener) {
		this.params = params;
		this.listener = listener;
		App.getInstance().addToRequestQueue(newRequest(Request.Method.GET, url));
	}

	/**
	 * description: POST请求 <br>
	 * author: dongyeforever@foxmail.com <br>
	 * @param params 请求参数
	 * @param listener 返回数据处理
	 * date: 2015/6/16 20:09 <br>
	 * version: 1.0
	 */
	public void post(String url, Map<String, String> params, ResponseListener listener) {
		this.params = params;
		this.listener = listener;
		App.getInstance().addToRequestQueue(newRequest(Request.Method.POST, url));
	}

	/**
	 * description <br>
	 * author dongyeforever@foxmail.com <br>
	 * @param method 请求类型 <br>
	 * date 2015/5/21 19:24 <br>
	 * version 1.0
	 */
	private StringRequest newRequest(int method,String url) {

		return new StringRequest(method, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 处理返回的JSON数据
						try {
							listener.onSuccess(response);
						} catch (IndexOutOfBoundsException e) {
							LogUtil.e(e.toString());
						} 
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						String errorType = GetNetError.getNetError(error);
						listener.onFailure(errorType);
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				// POST 参数
				return params;
			}
		};
	}


}
