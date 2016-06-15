package com.liz.mj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-24 21:19
 * version:
 */
public class Album extends BmobObject {
    private BmobFile pictures;

    public BmobFile getPictures() {
        return pictures;
    }

    public void setPictures(BmobFile pictures) {
        this.pictures = pictures;
    }
}
