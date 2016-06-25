package com.liz.mj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-10 13:14
 * version:
 */
public class MJAlbums extends BmobObject {
    private String albumName;
    private String albumPublishedTime;
    private BmobFile albumCover;
    private String albumDescription;
    private String albumId;
    private String albumPlayNum;
    private BmobRelation albumIncludeSongs;
    private MJSongs includingSongs;

    public MJSongs getIncludingSongs() {
        return includingSongs;
    }

    public void setIncludingSongs(MJSongs includingSongs) {
        this.includingSongs = includingSongs;
    }

    public BmobRelation getAlbumIncludeSongs() {
        return albumIncludeSongs;
    }

    public void setAlbumIncludeSongs(BmobRelation albumIncludeSongs) {
        this.albumIncludeSongs = albumIncludeSongs;
    }

    public String getAlbumPlayNum() {
        return albumPlayNum;
    }

    public void setAlbumPlayNum(String albumPlayNum) {
        this.albumPlayNum = albumPlayNum;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumPublishedTime() {
        return albumPublishedTime;
    }

    public void setAlbumPublishedTime(String albumPublishedTime) {
        this.albumPublishedTime = albumPublishedTime;
    }

    public BmobFile getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(BmobFile albumCover) {
        this.albumCover = albumCover;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
