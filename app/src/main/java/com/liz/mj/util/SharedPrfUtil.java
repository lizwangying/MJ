package com.liz.mj.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.liz.mj.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author zhengjuxin
 *
 */
public class SharedPrfUtil {

	public static String name = "app_data";

	private SharedPrfUtil(){}

	/**
	 * @param key 存储的key
	 * @param object 存储的对象
	 */
	public static void setObject(String key ,Object object){
		if(object!=null){
			ByteArrayOutputStream bOut = null;
			ObjectOutputStream objOut = null;
			try {
				bOut = new ByteArrayOutputStream();
				objOut = new ObjectOutputStream(bOut);
				objOut.writeObject(object);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//写入信息
			if(objOut!=null) {
				SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putString(key, Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT));
				editor.apply();
			}
		}else{
			SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString(key, "");
			editor.apply();
		}
	}

	/**
	 * @param key 存储的key
	 * @return 存储的对象
	 */
	public static Object getObject(String key){
		//从文件读取
		Object obj = null;
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		String data = sp.getString(key,null);
		if(data!=null) {
			try {
				ByteArrayInputStream bIn = new ByteArrayInputStream(Base64.decode(data, Base64.DEFAULT));
				ObjectInputStream objIn = new ObjectInputStream(bIn);
				obj = objIn.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 存储字符串
	 * @param key key
	 * @param value 值
	 */
	public static boolean setString(String key ,String value){
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null && key != null){
			SharedPreferences.Editor editor = sp.edit();
			editor.putString(key, value);
			return editor.commit();
		}
		return false;
	}
	/**
	 * 存储字符串
	 * @param key key
	 * @param value 值
	 */
	public static boolean setString(Context context ,String key ,String value){
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null && key != null){
			SharedPreferences.Editor editor = sp.edit();
			editor.putString(key, value);
			return editor.commit();
		}
		return false;
	}

	/**
	 * 存储一组字符串
	 *
	 */
	public static boolean setStrings(Context context ,String[] keys, String[] values){
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null && keys.length == values.length){
			SharedPreferences.Editor editor = sp.edit();
			for (int i = 0; i < keys.length; i++) {
				editor.putString(keys[i] ,values[i]);
			}
			return editor.commit();
		}
		return false;
	}

	/**
	 * 获取字符串
	 *
	 */
	public static String getString(Context context ,String key){
		String value = null;
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null){
			value = sp.getString(key, null);
		}
		return value;
	}

	/**
	 * 获取字符串
	 *
	 */
	public static String getString(String key){
		String value = null;
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null){
			value = sp.getString(key, null);
		}
		return value;
	}

	/**
	 * 存储int类型
	 *
	 */
	public static boolean setInt(Context context ,String key ,int value){
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null && key != null){
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt(key, value);
			return editor.commit();
		}
		return false;
	}
	/**
	 * 存储int类型
	 *
	 */
	public static boolean setInt(String key ,int value){
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null && key != null){
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt(key, value);
			return editor.commit();
		}
		return false;
	}

	/**
	 * 获取int类型
	 *
	 */
	public static int getInt(String key){
		int value = -1;
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null){
			value = sp.getInt(key, -1);
		}
		return value;
	}

	/**
	 * 存储float类型
	 *
	 */
	public static boolean setFloat(String key ,float value){
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null && key != null){
			SharedPreferences.Editor editor = sp.edit();
			editor.putFloat(key, value);
			return editor.commit();
		}
		return false;
	}

	/**
	 * 获取float类型
	 *
	 */
	public static float getFloat(String key){
		float value = -1;
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null){
			value = sp.getFloat(key, 0f);
		}
		return value;
	}

	/**
	 * 存储Set<String>集合
	 *
	 */
	/*public static boolean setStringSet(String key ,Set<String> value) {
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null && key != null){
			SharedPreferences.Editor editor = sp.edit();
			editor.putStringSet(key, value);
			return editor.commit();
		}
		return false;
	}*/

	/**
	 * 获取Set<String>集合
	 *
	 */
	/*public static Set<String> getStringSet(String key) {
		Set<String> value = new HashSet<>();
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null) {
			value = sp.getStringSet(key, value);
		}
		return value;
	}*/

	/**
	 * 存储boolean类型
	 *
	 */
	public static boolean setBoolean(String key ,boolean value){
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null && key != null){
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean(key, value);
			return editor.commit();
		}
		return false;
	}


	/**
	 * 获取boolean类型
	 *
	 */
	public static boolean getBoolean(String key){
		boolean value = false;
		SharedPreferences sp = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null){
			value = sp.getBoolean(key, false);
		}
		return value;
	}
	/**
	 * 获取int类型
	 *
	 */
	public static int getInt(Context context ,String key){
		int value = -1;
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		if(sp !=null){
			value = sp.getInt(key, -1);
		}
		return value;
	}
}
