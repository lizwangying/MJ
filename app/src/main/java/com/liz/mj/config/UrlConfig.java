package com.liz.mj.config;

public class UrlConfig {
	public static final String ROOT_URL = "http://10.0.1.250:9010/cbbpro/%s";

	public static final String ORDER_PATH = "orderAction";
	public static final String SELLER_PATH = "sellerAction";
	public static final String USER_PATH = "userAction";
	public static final String SYSTEM_PATH = "systemAction";

	//
	/**
	 * 得到请求的绝对路径
	 * @param partUrl Action url
	 */
	public static String getAbsoluteUrl(String partUrl) {
		return String.format(ROOT_URL, partUrl);
	}
}
