package com.liz.mj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-23 19:17
 * version:
 */
public class MJVideos extends BmobObject {
    private String videoAddress;
    private String videoName;
    private BmobFile videoCover;

    public String getVideoAddress() {
        return videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public BmobFile getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(BmobFile videoCover) {
        this.videoCover = videoCover;
    }
}
