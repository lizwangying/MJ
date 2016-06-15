package com.liz.mj.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.bean.WarmStorys;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.listener.UpdateListener;

public class StoryDetailActivity extends BaseActivity {
    @Bind(R.id.text_story_content)
    TextView warmStory;
    @Bind(R.id.text_story_title)
    TextView textStoryTitle;
    @Bind(R.id.text_seen)
    TextView textSeen;
    @Bind(R.id.text_like)
    TextView textLike;
    @Bind(R.id.image_item_hot_topic)
    SimpleDraweeView coverImage;

    private WarmStorys storys;
    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_story_detail);
    }

    @Override
    public void initView() {
        Intent intent = this.getIntent();
        storys = (WarmStorys) intent.getSerializableExtra("WarmStorys");
        warmStory.setText(storys.getStoryContent());
        textStoryTitle.setText(storys.getStoryTitle());
        textSeen.setText(storys.getStorySeen()+"");
        textLike.setText(storys.getStoryLiked()+"");
        String imageUri = storys.getStoryIcon().getFileUrl(StoryDetailActivity.this);
        Uri uri = Uri.parse(imageUri);
        coverImage.setImageURI(uri);
    }

    @OnClick({R.id.text_like})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.text_like:
                storys.increment("storyLiked");
                storys.update(StoryDetailActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        showToast("喜欢成功+1");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("喜欢失败");

                    }
                });
                break;
        }
    }
}
