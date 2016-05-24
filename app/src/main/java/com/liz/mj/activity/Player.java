package com.liz.mj.activity;

import android.media.MediaPlayer;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-14 10:46
 * version:
 */
public class Player implements MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener{

    /**
     *
     * @param mediaPlayer
     * @param i
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    /**
     *
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    /**
     *
     * @param mediaPlayer
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
