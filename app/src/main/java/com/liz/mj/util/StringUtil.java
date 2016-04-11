package com.liz.mj.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 
	 * @Description:  验证是否为空
	 * @Title:        isEmpty  
	 * @author        yy
	 * @Date          2014-11-10 下午5:11:25
	 * @throws
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		return false;
	}

	/**
	 * 
	 * @Description:  验证手机号
	 * @Title:        isMobileNO  
	 * @author        yy
	 * @Date          2014-11-10 下午5:12:00
	 * @throws
	 */
	public static boolean isMobileNO(String mobile) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);

		return m.matches();
	}
	
	/**
	 * 
	 * @Description:  获取当前时间
	 * @Title:        formatCurrentTimeToTimeString  
	 * @author        yy
	 * @Date          2014-11-10 下午5:12:21
	 * @throws
	 */
	public static String formatCurrentTimeToTimeString(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/**
	 * 
	 * @Description:  验证邮箱
	 * @Title:        isEmail  
	 * @author        yy
	 * @Date          2014-11-10 下午5:12:45
	 * @throws
	 */
	public static boolean isEmail(String Email) {
		boolean isValid = false;
		String expression = "(?=^[\\w.@]{6,50}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}";
		if (Email.matches(expression)) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * 
	 * @Description:  验证车牌号
	 * @Title:        isLicenseNumber  
	 * @author        yy
	 * @Date          2014-11-10 下午5:12:59
	 * @throws
	 */
	public static boolean isLicenseNumber(String licensenumber) {
		Pattern p = Pattern
				.compile("^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z0-9]{5}$");
		Matcher m = p.matcher(licensenumber);

		return m.matches();
	}
	/**
	 * 验证金额
	 * @Description: 
	 * @Title:        isBigDecimal  
	 * @author        yy
	 * @Date          2014-11-10 下午5:13:18
	 * @throws
	 */
    public static boolean isBigDecimal(String str) {
		Pattern p = Pattern
				.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))?$");
		Matcher m = p.matcher(str);

		return m.matches();
    
    }
    /**
     * 校验密码 6到16位，字母和数字
     * @Description: 
     * @Title:        isPassword  
     * @author        yzr
     * @Date          2014-11-19 下午5:23:51
     * @throws
     */
	public static boolean isPassword(String str) {
		Pattern p = Pattern
				.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z~!@#$%^&*_+`\\-={}:\";'<>?,./()]{6,16}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
    /**
     * 
     * @Description: 	获取APP版本号
     * @Title:        getVersionName  
     * @author        yy
     * @Date          2014-11-10 下午5:13:38
     * @throws
     */
	public static String getVersionName(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo == null ? "" : packInfo.versionName;
	}

	/**
	 * 获取当前时间格式
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param separator
	 * @return
	 */
	public static String getTimeForm(int year, int month, int day,
			String separator) {
		StringBuilder builder = new StringBuilder();
		builder.append(year);
		if (separator != null) {
			builder.append(separator);
		}
		builder.append((month + 1) < 10 ? "0" + (month + 1) : (month + 1));
		if (separator != null) {
			builder.append(separator);
		}
		builder.append((day < 10) ? "0" + day : day);

		return builder.toString();
	}
	
}
