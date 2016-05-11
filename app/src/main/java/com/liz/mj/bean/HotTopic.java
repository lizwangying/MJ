package com.liz.mj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-04-25 17:31
 * version:
 */
public class HotTopic extends BmobObject {
    private String topic;
    private Integer seen;
    private Integer like;
    private BmobFile topicPic;

    public BmobFile getTopicPic() {
        return topicPic;
    }

    public void setTopicPic(BmobFile topicPic) {
        this.topicPic = topicPic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getSeen() {
        return seen;
    }

    public void setSeen(Integer seen) {
        this.seen = seen;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
}
