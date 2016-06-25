package com.liz.mj.config;


import com.amap.api.maps2d.model.LatLng;

public class Constants {
    public static final String UPDATE_ACTION = "com.liz.mj.action.UPDATE_ACTION";
    public static final String CTL_ACTION = "com.liz.mj.action.CTL_ACTION";
    public static final String MUSIC_CURRENT = "com.liz.mj.action.MUSIC_CURRENT";
    public static final String MUSIC_DURATION = "com.liz.mj.action.MUSIC_DURATION";
    public static final String REPEAT_ACTION = "com.liz.mj.action.REPEAT_ACTION";
    public static final String SHUFFLE_ACTION = "com.liz.mj.action.MUSIC_DURATION";
    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
    public static final int ERROR = 1001;// 网络异常
    public static final int ROUTE_START_SEARCH = 2000;
    public static final int ROUTE_END_SEARCH = 2001;
    public static final int ROUTE_BUS_RESULT = 2002;// 路径规划中公交模式
    public static final int ROUTE_DRIVING_RESULT = 2003;// 路径规划中驾车模式
    public static final int ROUTE_WALK_RESULT = 2004;// 路径规划中步行模式
    public static final int ROUTE_NO_RESULT = 2005;// 路径规划没有搜索到结果

    public static final int GEOCODER_RESULT = 3000;// 地理编码或者逆地理编码成功
    public static final int GEOCODER_NO_RESULT = 3001;// 地理编码或者逆地理编码没有数据
}
