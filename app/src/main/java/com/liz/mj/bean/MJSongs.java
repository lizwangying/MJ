package com.liz.mj.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-11 13:05
 * version:
 */
public class MJSongs extends BmobObject implements Serializable{
    private String songName;
    private String albumId;
    private BmobFile songFile;
    private MJAlbums albumName;
    private String albumNameString;
    private Integer musicTime;
    private BmobRelation belongAlbum;

    public BmobRelation getBelongAlbum() {
        return belongAlbum;
    }

    public void setBelongAlbum(BmobRelation belongAlbum) {
        this.belongAlbum = belongAlbum;
    }

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

    public Integer getMusicTime() {
        return musicTime;
    }

    public void setMusicTime(Integer musicTime) {
        this.musicTime = musicTime;
    }

    public void setSongFile(BmobFile songFile) {
        this.songFile = songFile;
    }
}
