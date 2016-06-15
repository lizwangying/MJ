package com.liz.mj.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;
import com.liz.mj.R;
import com.liz.mj.config.Constants;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;

/**
 * description: 全球粉丝 地图打点
 * author: https://github.com/lizwangying
 * date:  2016/5/23 22:55
 */
public class FansGlobalActivity extends Activity implements AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener,
         AMap.InfoWindowAdapter {
    MapView mapView;
    ImageView imageBack;
    private MarkerOptions markerOption;
    private TextView markerText;
    private Button markerButton;// 获取屏幕内所有marker的button
    private RadioGroup radioOption;
    private AMap aMap;
    private Marker marker2;// 有跳动效果的marker对象
    private LatLng latlng = new LatLng(36.061, 103.834);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_global);
        //初始化Bmob
        Bmob.initialize(this,"3c5885ebb8bb40b1c7b3b3a818d17f7c");
        // 通过注解绑定控件
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        initView();
    }

    public void initView() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        addMarkersToMap();// 往地图上添加marker
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {

        //文字显示标注，可以设置显示内容，位置，字体大小颜色，背景色旋转角度,Z值等
        TextOptions textOptions = new TextOptions().position(Constants.BEIJING)
                .text("迈迷中心").fontColor(Color.BLACK)
                .backgroundColor(Color.WHITE).fontSize(30).rotate(0).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD);
        aMap.addText(textOptions);

        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(Constants.CHENGDU).title("成都市")
                .snippet("成都市:30.679879, 104.064855").draggable(true));

        markerOption = new MarkerOptions();
        markerOption.position(Constants.XIAN);
        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
        markerOption.draggable(true);
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.mipmap.arrow));
        marker2 = aMap.addMarker(markerOption);
        marker2.showInfoWindow();
        // marker旋转90度
        marker2.setRotateAngle(90);

        // 动画效果
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(Constants.ZHENGZHOU).title("郑州市").icons(giflist)
                .draggable(true).period(10));

        drawMarkers();// 添加10个带有系统默认icon的marker
    }

    /**
     * 绘制系统默认的1种marker背景图片
     */
    public void drawMarkers() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("兔兔迈粉")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }




//    @OnClick({R.id.image_back})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.image_back:
//                finish();
//                break;
//        }
//    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
//        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_contents) {
//            return null;
//        }
        View infoContent = getLayoutInflater().inflate(
                R.layout.custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }
    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
//        if (radioOption.getCheckedRadioButtonId() == R.id.custom_info_contents) {
//            ((ImageView) view.findViewById(R.id.badge))
//                    .setImageResource(R.drawable.badge_sa);
//        } else if (radioOption.getCheckedRadioButtonId() == R.id.custom_info_window) {
//            ImageView imageView = (ImageView) view.findViewById(R.id.badge);
//            imageView.setImageResource(R.drawable.badge_wa);
//        }
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
//        showToast("你点击了infoWindow窗口" + marker.getTitle());
        Toast.makeText(FansGlobalActivity.this,"窗口"+marker.getTitle(),Toast.LENGTH_SHORT);
    }

    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(Constants.XIAN).include(Constants.CHENGDU)
                .include(latlng).include(Constants.ZHENGZHOU).include(Constants.BEIJING).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(marker2)) {
            if (aMap != null) {
                jumpPoint(marker);
            }
        }
        markerText.setText("你点击的是" + marker.getTitle());
        return false;
    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point startPoint = proj.toScreenLocation(Constants.XIAN);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * Constants.XIAN.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * Constants.XIAN.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                aMap.invalidate();// 刷新地图
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
//        String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
//                + marker.getPosition().latitude + ","
//                + marker.getPosition().longitude + ")";
//        markerText.setText(curDes);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
//        markerText.setText(marker.getTitle() + "停止拖动");
    }
}
