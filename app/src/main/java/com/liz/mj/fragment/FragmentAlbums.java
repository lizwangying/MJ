package com.liz.mj.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.liz.mj.R;
import com.liz.mj.activity.AlbumsListActivity;
import com.liz.mj.activity.MusicPlayActivity;
import com.liz.mj.activity.VideoWebViewActivity;
import com.liz.mj.adapter.RecyclerAlbumAdapter;
import com.liz.mj.adapter.RecyclerSongsAdapter;
import com.liz.mj.adapter.RecyclerVideosAdapter;
import com.liz.mj.bean.MJAlbums;
import com.liz.mj.bean.MJSongs;
import com.liz.mj.bean.MJVideos;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAlbums.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAlbums#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAlbums extends Fragment {
    @Bind(R.id.text_hot_topic)
    TextView textViewAlbumList;
//    @Bind(R.id.text_more)
//    TextView textViewMore;

    @Bind(R.id.view_songs_headline)
    View viewHeadlineSongs;
    private TextView textHeadlineSongs;

    @Bind(R.id.view_hot_mv)
    View viewHotMV;
    private TextView textHotMV;

    @Bind(R.id.recycler_view_albums)
    RecyclerView recyclerViewAlbums;

    private List<MJAlbums> albumsList;

    @Bind(R.id.recycler_view_songs)
    RecyclerView recyclerViewSongs;
    @Bind(R.id.recycler_view_mv)
    RecyclerView recyclerViewsVideos;
    private RecyclerSongsAdapter songsAdapter;
    private RecyclerAlbumAdapter albumAdapter;
    private RecyclerVideosAdapter videosAdapter;
    private List<MJVideos> videosList;

    private List<MJSongs> songsLists;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentAlbums() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAlbums.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAlbums newInstance(String param1, String param2) {
        FragmentAlbums fragment = new FragmentAlbums();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_albums, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();

        return view;
    }

    private void initView() {
        textHeadlineSongs = (TextView) viewHeadlineSongs.findViewById(R.id.text_hot_topic);
        textHotMV = (TextView) viewHotMV.findViewById(R.id.text_hot_topic);
        textViewAlbumList.setText("专辑");
        textHeadlineSongs.setText("歌曲");
        textHotMV.setText("MV");
        textHeadlineSongs.setCompoundDrawables(getResources().getDrawable(R.mipmap.icon_music)
                , null, null, null);
        textViewAlbumList.setCompoundDrawables(getResources().getDrawable(R.mipmap.icon_album)
                , null, null, null);
        textHotMV.setCompoundDrawables(getResources().getDrawable(R.mipmap.icon_video)
                , null, null, null);

        recyclerViewAlbums.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewsVideos.setLayoutManager(new LinearLayoutManager(getActivity()));



//        //
//        MJAlbums albums = new MJAlbums();


    }

    /**
     * description: 初始化数据，还是要加进去缓存才好
     * date:  2016/5/10 17:23
     */
    private void initData() {
        String sql = "select * from MJAlbums";
        new BmobQuery<MJAlbums>().doSQLQuery(getActivity(), sql, new SQLQueryListener<MJAlbums>() {
            @Override
            public void done(BmobQueryResult<MJAlbums> bmobQueryResult, BmobException e) {
                if (e == null) {
                    albumsList = bmobQueryResult.getResults();
//                    MJAlbums al = albumsList.get(1);
//                    MJSongs song = new MJSongs();
//                    song.setObjectId("xP0ehhhs");
//                    al.setIncludingSongs(song);
//                    al.save(getActivity(), new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            Log.i("life","评论发表成功");
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//
//                        }
//                    });




                    if (albumsList != null && albumsList.size() > 0) {
//                        albumGridView.setAdapter(new AlbumGridViewAdapter(albumsList,getActivity()));
                        albumAdapter = new RecyclerAlbumAdapter(albumsList, getActivity());
                        recyclerViewAlbums.setAdapter(albumAdapter);
                        albumAdapter.setOnItemClickListener(new RecyclerAlbumAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), AlbumsListActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("MJAlbum", albumsList.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
//                        albumAdapter.seto
                    } else {
                        Toast.makeText(getActivity(), "怎么回事？没有数据？淡定，让我想想~~~", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                    Log.i("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });

        String bql = "select * from MJSongs";
        new BmobQuery<MJSongs>().doSQLQuery(getActivity(), bql, new SQLQueryListener<MJSongs>() {
            @Override
            public void done(BmobQueryResult<MJSongs> bmobQueryResult, BmobException e) {
                if (e == null) {
                    songsLists = bmobQueryResult.getResults();

//                    //添加多对多关联
//                    MJAlbums a = new MJAlbums();
//                    MJSongs s = songsLists.get(0);
//                    a.setObjectId("qe6TDDDH");
//                    BmobRelation relation = new BmobRelation();
//                    relation.add(a);
//                    s.setBelongAlbum(relation);
//                    s.update(getActivity(), new UpdateListener() {
//                        @Override
//                        public void onSuccess() {
//                            Toast.makeText(getActivity(),"添加关系成功",Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//
//                        }
//                    });

                    if (songsLists != null && songsLists.size() > 0) {
                        songsAdapter = new RecyclerSongsAdapter(getActivity(), songsLists);
                        recyclerViewSongs.setAdapter(songsAdapter);
                        songsAdapter.setOnItemClickListener(new RecyclerSongsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), MusicPlayActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("MJSongs", songsLists.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(getActivity(), "long click" + position, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "怎么回事？没有数据？淡定，让我想想~~~", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                    Log.i("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });

        String bqlVideo = "select * from MJVideos";
        new BmobQuery<MJVideos>().doSQLQuery(getActivity(), bqlVideo, new SQLQueryListener<MJVideos>() {
            @Override
            public void done(BmobQueryResult<MJVideos> bmobQueryResult, BmobException e) {
                if (e == null) {
                    videosList = bmobQueryResult.getResults();
                    if (videosList != null && videosList.size() > 0) {
                        videosAdapter = new RecyclerVideosAdapter(getActivity(), videosList);
                        recyclerViewsVideos.setAdapter(videosAdapter);
                        Log.e("haha","setadapter-----");
                        videosAdapter.setOnItemClickListener(new RecyclerVideosAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), VideoWebViewActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("MJVideos", videosList.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(getActivity(), "long click" + position, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "怎么回事？没有数据？淡定，让我想想~~~", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                    Log.i("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });


        //添加一对一关系
//        MJSongs songs =
        //查询歌曲名称并添加Pointer关系，注入歌曲属于哪个专辑
//        final String[] albumId = new String[1];
//        BmobQuery<MJAlbums> query = new BmobQuery<>();
//        query.addWhereEqualTo("albumName","Invincible");
//        query.setLimit(1);
//        query.findObjects(getActivity(), new FindListener<MJAlbums>() {
//            @Override
//            public void onSuccess(List<MJAlbums> list) {
//                MJAlbums album = list.get(0);//歌曲对象
//                albumId[0] = album.getObjectId();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Toast.makeText(getActivity(),"查找歌曲失败",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        BmobQuery<MJSongs> song = new BmobQuery<>();
//        song.addWhereEqualTo("songName","Unbreakable");
//        song.findObjects(getActivity(), new FindListener<MJSongs>() {
//            @Override
//            public void onSuccess(List<MJSongs> list) {
//                MJSongs songGet = list.get(0);
//                songGet.getAlbumName();
//                MJAlbums al = new MJAlbums();
//                al.setObjectId(albumId[0]);
//                songGet.setAlbumName(al);
//                songGet.update(getActivity(), songGet.getObjectId(), new UpdateListener() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
