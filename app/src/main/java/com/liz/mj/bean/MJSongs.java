package com.liz.mj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-11 13:05
 * version:
 */
public class MJSongs extends BmobObject {
    private String songName;
    private String albumId;
    private BmobFile songFile;
    private MJAlbums albumName;
    private String albumNameString;

    public String getAlbumNameString() {
        return albumNameString;
    }

    public void setAlbumNameString(String albumNameString) {
        this.albumNameString = albumNameString;
    }

    public MJAlbums getAlbumName() {
        return albumName;
    }

    public void setAlbumName(MJAlbums albumName) {
        this.albumName = albumName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public BmobFile getSongFile() {
        return songFile;
    }

    public void setSongFile(BmobFile songFile) {
        this.songFile = songFile;
    }
}
