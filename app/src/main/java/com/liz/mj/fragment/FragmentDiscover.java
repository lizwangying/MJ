package com.liz.mj.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.R;
import com.liz.mj.activity.LoginActivity;
import com.liz.mj.activity.PhotoActivity;
import com.liz.mj.adapter.RecyclerAlbumPhotoAdapter;
import com.liz.mj.bean.Album;
import com.liz.mj.bean.MyUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDiscover.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDiscover#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDiscover extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @Bind(R.id.text_user_email)
    TextView textUserEmail;

    @Bind(R.id.text_user_name)
    TextView textUserName;
    @Bind(R.id.image_user_icon)
    SimpleDraweeView iconUser;
    @Bind(R.id.text_follow)
    TextView textFollow;
    @Bind(R.id.text_like_me)
    TextView textLikeMe;
    @Bind(R.id.text_description)
    TextView textDescription;
    @Bind(R.id.recycler_album)
    RecyclerView recyclerViewAlbum;
    private RecyclerAlbumPhotoAdapter adapter;
    private List<Album> albumPhotoList;
    @Bind(R.id.button_logout)
    Button buttonLogout;
    MyUser user;
//    @Bind()

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDiscover newInstance(String param1, String param2) {
        FragmentDiscover fragment = new FragmentDiscover();
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
        View view = inflater.inflate(R.layout.activity_personal_information, container, false);
        ButterKnife.bind(this, view);
        //判断用户是否登录
        user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        initView();
        if (user != null) {
            initData();

        }
        initAlbum();
        return view;
    }

    private void initAlbum() {
        String sql = "select * from Album";
        new BmobQuery<Album>().doSQLQuery(getActivity(), sql, new SQLQueryListener<Album>() {
            @Override
            public void done(BmobQueryResult<Album> bmobQueryResult, BmobException e) {
                if (e == null) {
                    albumPhotoList = bmobQueryResult.getResults();
                    if (albumPhotoList != null && albumPhotoList.size() > 0) {
                        adapter = new RecyclerAlbumPhotoAdapter(getActivity(), albumPhotoList);
                        recyclerViewAlbum.setAdapter(adapter);
                        adapter.setOnItemClickListener(new RecyclerAlbumPhotoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Album", albumPhotoList.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "怎么回事？没有数据？淡定，让我想想~~~", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                    Log.e("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });
    }

    private void initView() {

        if (user == null) {
            textUserName.setText("走~登录走~");
            textUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }
        Log.e("haha", "错误码：33333333333333333330000000--------------"+user.getUsername());
        if (user.getUsername() != null){
            textUserName.setText("初级迈迷");
            textUserEmail.setText(user.getEmail());
            textUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }


        recyclerViewAlbum.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));

    }

    private void initData() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("objectId",
                BmobUser.getCurrentUser(getActivity(), MyUser.class).getObjectId());
        query.findObjects(getActivity(), new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> object) {
                MyUser newMyUser = object.get(0);

                try {
                    if (newMyUser.getUserIcon().getFileUrl(getActivity()) != null) {

                        iconUser.setImageURI(Uri.parse(newMyUser.getUserIcon().getFileUrl(getActivity())));
                    } else {
//                    iconUser.setImageURI(Uri.parse());

                    }
                } catch (NullPointerException NnullPointerException) {

                }

                textFollow.setText(newMyUser.getFollowMFans() + "");
                textLikeMe.setText(newMyUser.getLikeMeMFans() + "");
                String des = newMyUser.getDescription();
                Log.e("haha", "onSuccess: --------------"+des);
                if (des != null) {
                    textDescription.setText(des);
                }else{
                    textDescription.setText("我会继续努力完善App，您先别着急卸载啊，等着更新啊，谢谢" +
                            "当我的脑残粉。哈哈~这个位置是您的座右铭，让更多迈迷关注你吧~");

                }
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(getActivity(), "登录信息查询失败，重新登录吧", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @OnClick({R.id.text_user_name,R.id.button_logout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.text_user_name:
//                startActivity(new Intent(getActivity(),));
                break;
            case R.id.button_logout:
                BmobUser.logOut(getActivity());   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(getActivity()); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(),LoginActivity.class));

                break;
        }
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
