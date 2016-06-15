package com.liz.mj.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.adapter.RecyclerCommentsAdapter;
import com.liz.mj.bean.HotTopic;
import com.liz.mj.bean.TopicComments;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class HotTopicDetail extends BaseActivity {
    @Bind(R.id.recycler_hot_topic)
    RecyclerView recyclerViewHotTopic;
    @Bind(R.id.icon_back)
    ImageView iconBack;
    @Bind(R.id.icon_add_comments)
    ImageView iconAddComments;
    private RecyclerCommentsAdapter adapterComments;

    @Bind(R.id.text_topic_title)
    TextView topicTitle;
    @Bind(R.id.image_item_hot_topic)
    SimpleDraweeView imageHopTopic;
    private HotTopic topic;

    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_hot_topic_detail);
    }

    @Override
    public void initView() {
        Intent intent = this.getIntent();
        topic = (HotTopic) intent.getSerializableExtra("Comments");
        topicTitle.setText(topic.getTopic());
        recyclerViewHotTopic.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        imageHopTopic.setImageURI(Uri.parse(topic.getTopicPic().getFileUrl(HotTopicDetail.this)));
    }

    @Override
    public void initData() {
        BmobQuery<TopicComments> query = new BmobQuery<>();
        query.addWhereEqualTo("commentTopic",topic);
        query.order("-updatedAt");
//        query.include("");
        query.findObjects(getBaseContext(), new FindListener<TopicComments>() {
            @Override
            public void onSuccess(List<TopicComments> list) {
                if (list!=null && list.size()>0){
                    adapterComments = new RecyclerCommentsAdapter(HotTopicDetail.this,list);
                    recyclerViewHotTopic.setAdapter(adapterComments);
                    adapterComments.setOnItemClickListener(new RecyclerCommentsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                }
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @OnClick({R.id.icon_back,R.id.icon_add_comments})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.icon_back:
                finish();
                break;
            case R.id.icon_add_comments:
                Intent in = new Intent(HotTopicDetail.this,AddCommentsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Comments", getIntent().getSerializableExtra("Comments"));
                in.putExtras(bundle);
                startActivity(in);
                break;

        }
    }
}
