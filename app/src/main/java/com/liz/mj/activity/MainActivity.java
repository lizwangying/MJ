package com.liz.mj.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.adapter.FindTabAdapter;
import com.liz.mj.fragment.FragmentAlbums;
import com.liz.mj.fragment.FragmentDiscover;
import com.liz.mj.fragment.FragmentSongs;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements
        FragmentAlbums.OnFragmentInteractionListener,FragmentDiscover.OnFragmentInteractionListener{

//    @Bind(R.id.alertdialog)
//    Button alertDialog;
//    private AlertDialog mAlertDialog;
    @Bind(R.id.view_tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_viewpager)
    ViewPager viewPager;
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private FragmentSongs fragmentSongs;
    private FragmentAlbums fragmentAlbums;
    private FragmentDiscover fragmentDiscover;
    private FindTabAdapter findTabAdapter;

    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_main);
        setOverlayViewId(R.id.gov_main);
    }

    @Override
    public void initView() {
        fragmentSongs = new FragmentSongs();
        fragmentDiscover = new FragmentDiscover();
        fragmentAlbums = new FragmentAlbums();

        listFragment = new ArrayList<>();
        listFragment.add(fragmentSongs);
        listFragment.add(fragmentAlbums);
        listFragment.add(fragmentDiscover);

        listTitle = new ArrayList<>();
        listTitle.add("主页");
        listTitle.add("专辑");
        listTitle.add("发现");

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(2)));

        findTabAdapter = new FindTabAdapter(getSupportFragmentManager(),listFragment,listTitle);
        viewPager.setAdapter(findTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.alertdialog:
////                mAlertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                TranslantAlertDialog.Builder builder = new TranslantAlertDialog.Builder(MainActivity.this);
//                builder.setMessage("开心么？");
//                builder.setPositiveButton("开心", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
////                        Toast.makeText(MainActivity.this, "开心就好", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setNegativeButton("不开心", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        startActivity(new Intent(MainActivity.this,LoginActivitySystem.class));
////                        Toast.makeText(MainActivity.this,"不开心也好",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.create().show();
//                break;
//        }

    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

