package com.liz.mj.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.adapter.RecyclerHopTopicAdapter;
import com.liz.mj.bean.HotTopic;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class HotTopicDetail extends BaseActivity {
    @Bind(R.id.recycler_hot_topic)
    RecyclerView recyclerViewHotTopic;
    @Bind(R.id.icon_back)
    ImageView iconBack;
    @Bind(R.id.icon_add_comments)
    ImageView iconAddComments;
    private RecyclerHopTopicAdapter adapterHotTopic;

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
        imageHopTopic.setImageURI(Uri.parse(topic.getTopicPic().getFileUrl(HotTopicDetail.this)));
    }

    @Override
    public void initData() {
        String bql = "select * from HotTopic";
        new BmobQuery<HotTopic>().doSQLQuery(HotTopicDetail.this, bql, new SQLQueryListener<HotTopic>() {
            @Override
            public void done(BmobQueryResult<HotTopic> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<HotTopic> list = bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {
                        adapterHotTopic = new RecyclerHopTopicAdapter(list, HotTopicDetail.this);
                        recyclerViewHotTopic.setAdapter(adapterHotTopic);
                        adapterHotTopic.setOnItemtClickListener(new RecyclerHopTopicAdapter.OnItemtClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                startActivity(new Intent(HotTopicDetail.this,HotTopicDetail.class));
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {}
                        });

                        //动态设置listview的长度
//                        UIUtil.setListViewHeightBasedOnChildren(recyclerViewHotTopic);


                    } else {
                        Toast.makeText(HotTopicDetail.this, "无热门话题", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HotTopicDetail.this, "查询失败", Toast.LENGTH_SHORT).show();
                    Log.i("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
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
                startActivity(new Intent(HotTopicDetail.this,AddCommentsActivity.class));
                break;

        }
    }
}
