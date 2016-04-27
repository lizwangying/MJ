package com.liz.mj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-04-26 15:05
 * version:
 */
public class BannerPicture extends BmobObject {
    BmobFile imageResource;

    public BmobFile getImageResource() {
        return imageResource;
    }

    public void setImageResource(BmobFile imageResource) {
        this.imageResource = imageResource;
    }
}
