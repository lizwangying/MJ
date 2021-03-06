package com.liz.mj.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.liz.mj.R;
import com.liz.mj.activity.FansGlobalActivity;
import com.liz.mj.activity.HotTopicDetail;
import com.liz.mj.activity.MJPersonalInfo;
import com.liz.mj.activity.WarmStoryActivity;
import com.liz.mj.adapter.BannerImagePagerAdapter;
import com.liz.mj.adapter.RecyclerHopTopicAdapter;
import com.liz.mj.bean.BannerPicture;
import com.liz.mj.bean.HotTopic;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing
 * interface.
 */
public class FragmentSongs extends Fragment {
    @Bind(R.id.list_view_topic)
    RecyclerView recyclerViewHotTopic;
    @Bind(R.id.view_banner_viewpager)
    ViewPager bannerViewPager;


    @Bind(R.id.text_hot_topic)
    TextView text_topic;


    @Bind(R.id.text_crown)
    TextView textCrown;
    @Bind(R.id.text_fans_global)
    TextView textFansGlobal;
    @Bind(R.id.text_warm_story)
    TextView textWarmStory;
//    @Bind(R.id.text_more)
//    TextView textMore;

    private RecyclerHopTopicAdapter adapterHotTopic;

//    @Bind(R.id.image)
//    SimpleDraweeView img;

    private ScheduledExecutorService scheduledExecutorService;
    private BannerImagePagerAdapter bannerAdapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentSongs() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentSongs newInstance(int columnCount) {
        FragmentSongs fragment = new FragmentSongs();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_songs, container, false);
        ButterKnife.bind(this, view);
        //listview不可滑动
        recyclerViewHotTopic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });

        recyclerViewHotTopic.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
        initImage();
//        applyBlur();
        return view;
    }


//    private void applyBlur(){
//        //调用applyBlur的时候界面还没有开始布局，所以需要加监听事件是否可以开始进行模糊处理。
//        //需要布局文件经过measured, laid out. displayed的时候进行操作。
//        image_bg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                //去掉监听事件，因为不需要
//                image_bg.getViewTreeObserver().removeOnPreDrawListener(this);
//                //制作图片缓存
//                image_bg.buildDrawingCache();
//                //传给Bitmap
//                Bitmap bitmap = image_bg.getDrawingCache();
//                //开始高斯模糊
//                blur(bitmap,gaosi);
//                return true;//我猜测是有个渐进的过程，本来没有模糊，然后模糊的过程，false则不显示
//            }
//        });
//    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bitmap, View view){
        float radius = 20;
        //制作一个空的bitmap，大小为text的宽度和高度
        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
        //通过bitmap保存canvas的状态
        Canvas canvas = new Canvas(overlay);
        //在父布局文件中把Canvas移动到textview的位置
        canvas.translate(-view.getLeft(), -view.getTop());
        //把imageView的内容绘制到bitmap中
        canvas.drawBitmap(bitmap, 0, 0, null);
        //so we get a bitmap the same size as bitmap,It includes a part of ImageView
        //where the TextView back layer content.

        RenderScript rs = RenderScript.create(getActivity());
        //把bitmap复制一份到RenderScript需要的数据中
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        //创建RenderScript，模糊处理的实例
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);
        //设置输入，半径范围
        blur.setRadius(radius);

        blur.forEach(overlayAlloc);
        //把处理后的结果复制到之前的bitmap中
        overlayAlloc.copyTo(overlay);

        view.setBackground(new BitmapDrawable(
                getResources(), overlay));

        rs.destroy();

    }
    private void initImage() {
//        String picPath = "sdcard/temp.jpg";
//        BmobFile bmobFile = new BmobFile(new File(picPath));
        String sql = "select * from BannerPicture";
        new BmobQuery<BannerPicture>().doSQLQuery(getActivity(), sql, new SQLQueryListener<BannerPicture>() {
            @Override
            public void done(BmobQueryResult<BannerPicture> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<BannerPicture> list = bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {
                            BannerImagePagerAdapter adapter = new BannerImagePagerAdapter(getActivity(), list);
                            bannerViewPager.setAdapter(adapter);
//                            bannerViewPager.setCurrentItem();
//                            adapter.notifyDataSetChanged();//显示当前位置的item
                    } else {
                        Toast.makeText(getActivity(), "无banner图片", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                    Log.i("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });

    }

    private void initData() {
//        HotTopic hotTopic = new HotTopic();
//        hotTopic.setTopic("肚子疼死了我要回寝室睡觉！！！");
//        hotTopic.setLike(13);
//        hotTopic.setSeen(25);
//        hotTopic.save(getActivity(), new SaveListener() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(getActivity(), "存入", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
//
//            }
//        });
        String bql = "select * from HotTopic";
        new BmobQuery<HotTopic>().doSQLQuery(getActivity(), bql, new SQLQueryListener<HotTopic>() {
            @Override
            public void done(BmobQueryResult<HotTopic> bmobQueryResult, BmobException e) {
                if (e == null) {
                    final List<HotTopic> list = bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {
                        adapterHotTopic = new RecyclerHopTopicAdapter(list, getActivity());
                        recyclerViewHotTopic.setAdapter(adapterHotTopic);
                        adapterHotTopic.setOnItemtClickListener(new RecyclerHopTopicAdapter.OnItemtClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(),HotTopicDetail.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Comments", list.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            @Override
                            public void onItemLongClick(View view, int position) {}
                        });
                        //动态设置listview的长度
//                        UIUtil.setListViewHeightBasedOnChildren(recyclerViewHotTopic);
                    } else {
                        Toast.makeText(getActivity(), "无热门话题", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
//                    Log.i("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });

    }

    @OnClick({R.id.text_crown,R.id.text_fans_global,R.id.text_warm_story})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.text_crown:
                startActivity(new Intent(getActivity(), MJPersonalInfo.class));
                break;
            case R.id.text_fans_global:
                startActivity(new Intent(getActivity(), FansGlobalActivity.class));
                break;
            case R.id.text_warm_story:
                startActivity(new Intent(getActivity(), WarmStoryActivity.class));
                break;
//            case R.id.text_more:
//                startActivity(new Intent(getActivity(), HotTopicDetail.class));
//                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
