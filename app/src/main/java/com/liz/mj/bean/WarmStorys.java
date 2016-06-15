package com.liz.mj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-06-14 15:10
 * version:
 */
public class WarmStorys extends BmobObject{
    private String storyTitle;
    private String storyContent;
    private Integer storySeen;
    private Integer storyLiked;
    private BmobFile storyIcon;

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryContent() {
        return storyContent;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }

    public Integer getStorySeen() {
        return storySeen;
    }

    public void setStorySeen(Integer storySeen) {
        this.storySeen = storySeen;
    }

    public Integer getStoryLiked() {
        return storyLiked;
    }

    public void setStoryLiked(Integer storyLiked) {
        this.storyLiked = storyLiked;
    }

    public BmobFile getStoryIcon() {
        return storyIcon;
    }

    public void setStoryIcon(BmobFile storyIcon) {
        this.storyIcon = storyIcon;
    }
}
