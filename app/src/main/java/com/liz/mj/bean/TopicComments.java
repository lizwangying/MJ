package com.liz.mj.bean;

import cn.bmob.v3.BmobObject;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-24 16:17
 * version:
 */
public class TopicComments extends BmobObject {

    private MyUser commentAuthor;
    private HotTopic commentTopic;
    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public MyUser getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(MyUser commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public HotTopic getCommentTopic() {
        return commentTopic;
    }

    public void setCommentTopic(HotTopic commentTopic) {
        this.commentTopic = commentTopic;
    }
}
