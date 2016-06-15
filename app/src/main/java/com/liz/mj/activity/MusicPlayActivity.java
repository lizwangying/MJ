package com.liz.mj.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.bean.MJAlbums;
import com.liz.mj.bean.MJSongs;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;
import co.mobiwise.playerview.MusicPlayerView;
import co.mobiwise.playerview.OnPlayPauseToggleListener;

public class MusicPlayActivity extends BaseActivity implements OnPlayPauseToggleListener {
    @Bind(R.id.image_album_cover_bg)
    SimpleDraweeView coverBg;
    @Bind(R.id.music_play_view)
    MusicPlayerView musicPlayerView;
    @Bind(R.id.image_back)
    ImageView imageBack;
    @Bind(R.id.image_share)
    ImageView imageShare;
    @Bind(R.id.text_title)
    TextView textTitle;
    @Bind(R.id.image_next)
    ImageView imageNext;
    @Bind(R.id.image_previous)
    ImageView imagePrevious;

    private MJSongs mjSongs;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_music_play);

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        musicPlayerView.setProgressLoadedColor(Color.RED);
        musicPlayerView.setTimeColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void initView() {
        Intent intent = this.getIntent();
        mjSongs = (MJSongs) intent.getSerializableExtra("MJSongs");
        textTitle.setText(mjSongs.getSongName());
        String name = mjSongs.getSongName();
        if ("Unbreakable".equals(name)){
            mediaPlayer = MediaPlayer.create(MusicPlayActivity.this, R.raw.unbreakable);
        }else{
            mediaPlayer = MediaPlayer.create(MusicPlayActivity.this, R.raw.you_are_not_alone);
        }
        musicPlayerView.start();
        mediaPlayer.start();
        musicPlayerView.setProgress(mjSongs.getMusicTime());
        Log.e("haha", "-------------------------------"+mjSongs.getMusicTime() );
        musicPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isRotating = musicPlayerView.isRotating();
                if (isRotating) {
                    musicPlayerView.stop();
                    mediaPlayer.pause();
                } else {
                    musicPlayerView.start();
                    mediaPlayer.start();
                }
            }
        });
//        musicPlayerView.start();
//        mediaPlayer = MediaPlayer.create(MusicPlayActivity.this, R.raw.unbreakable);
//        mediaPlayer.start();
    }

    @Override
    public void initData() {

//        musicPlayerView.setCoverURL(mjSongs.get);
        BmobQuery<MJAlbums> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(MusicPlayActivity.this, mjSongs.getAlbumId(), new GetListener<MJAlbums>() {
            @Override
            public void onSuccess(MJAlbums mjAlbums) {
                musicPlayerView.setCoverURL(mjAlbums.getAlbumCover().getFileUrl(MusicPlayActivity.this));
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

        BmobFile bmobFile = mjSongs.getSongFile();
        if (bmobFile != null) {
            downLoadFile(bmobFile);
        }



    }

    private void downLoadFile(BmobFile bmobfFile) {

    }

    @OnClick({R.id.image_previous, R.id.image_next, R.id.image_share, R.id.image_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_previous:

                break;
            case R.id.image_next:
                break;
            case R.id.image_share:
                break;
            case R.id.image_back:
                mediaPlayer.stop();
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        finish();
    }

    @Override
    public void onToggled() {

    }
}
