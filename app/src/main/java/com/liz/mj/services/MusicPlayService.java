package com.liz.mj.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-15 11:18
 * version:
 */
public class MusicPlayService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
