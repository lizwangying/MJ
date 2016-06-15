package com.liz.mj.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-24 16:21
 * version:
 */
public class MyUser extends BmobUser {
    private BmobFile userIcon;
    private Integer followMFans;
    private Integer likeMeMFans;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFollowMFans() {
        return followMFans;
    }

    public void setFollowMFans(Integer followMFans) {
        this.followMFans = followMFans;
    }

    public Integer getLikeMeMFans() {
        return likeMeMFans;
    }

    public void setLikeMeMFans(Integer likeMeMFans) {
        this.likeMeMFans = likeMeMFans;
    }

    public BmobFile getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(BmobFile userIcon) {
        this.userIcon = userIcon;
    }
}
