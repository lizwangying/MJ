package com.dongye.mj.network;

import com.dongye.mj.config.UrlConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * description: App用到的接口
 * author: dongyeforever@foxmail.com
 * date: 2015-08-24 11:39
 * version: 1.0
 */
public class CbbApi {

    /**
     * 登录
     * @param userPhone 用户名
     * @param password 密码（md5）
     */
    public static void login(String userPhone, String password, ResponseListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("type", "login");
        params.put("logoName", userPhone);
        params.put("logoPass", password);
        VolleyStringRequest request = new VolleyStringRequest();
        request.post(UrlConfig.getAbsoluteUrl(UrlConfig.SELLER_PATH), params, listener);
    }

    /**
     * 忘记密码
     */
    public static void updateSellerPass(String phoneNum,String firstPassword, ResponseListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("type", "updateSellerPass");
        params.put("sellerPhone", phoneNum);
        params.put("logoPass", firstPassword);
        VolleyStringRequest request = new VolleyStringRequest();
        request.post(UrlConfig.getAbsoluteUrl(UrlConfig.USER_PATH), params, listener);
    }

    /**
     * description: 通用post请求 <br>
     * author: dongyeforever@foxmail.com <br>
     * date: 2015/6/17 11:48 <br>
     * version: 1.0
     */
    public static void post(String url, Map<String,String> params, ResponseListener listener) {
        VolleyStringRequest request = new VolleyStringRequest();
        request.post(UrlConfig.getAbsoluteUrl(url), params, listener);
    }
}
