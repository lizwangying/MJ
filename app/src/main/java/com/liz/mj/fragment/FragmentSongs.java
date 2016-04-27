package com.liz.mj.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.R;
import com.liz.mj.adapter.BannerImagePagerAdapter;
import com.liz.mj.adapter.HopTopicAdapter;
import com.liz.mj.bean.BannerPicture;
import com.liz.mj.bean.HotTopic;
import com.liz.mj.fragment.dummy.DummyContent;
import com.liz.mj.fragment.dummy.DummyContent.DummyItem;
import com.liz.mj.util.UIUtil;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentSongs extends Fragment {
    @Bind(R.id.list_view_topic)
    ListView listviewHotTopic;
    @Bind(R.id.view_banner_viewpager)
    ViewPager bannerViewPager;
    @Bind(R.id.image)
    SimpleDraweeView img;

    private ScheduledExecutorService scheduledExecutorService;
    private BannerImagePagerAdapter bannerAdapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

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
        listviewHotTopic.setOnTouchListener(new View.OnTouchListener() {
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
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }

        initData();
        initImage();
        return view;
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
                        for (BannerPicture picture : list) {
                            String imageUrlFromBmob = picture.getImageResource().getFileUrl(getActivity());
//                            imageUrl.add(imageUrlFromBmob);
                            //banner 图片viewpager设置适配器
                            Uri uri = Uri.parse(imageUrlFromBmob);

                            img.setImageURI(uri);



                            BannerImagePagerAdapter adapter = new BannerImagePagerAdapter(getActivity(), list);
                            bannerViewPager.setAdapter(adapter);
//                            bannerViewPager.setCurrentItem();
                            adapter.notifyDataSetChanged();//显示当前位置的item
                        }
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
                    List<HotTopic> list = (List<HotTopic>) bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {
                        listviewHotTopic.setAdapter(new HopTopicAdapter(list, getActivity()));
                        //动态设置listview的长度
                        UIUtil.setListViewHeightBasedOnChildren(listviewHotTopic);
                    } else {
                        Toast.makeText(getActivity(), "无热门话题", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                    Log.i("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
