package com.liz.mj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.adapter.RecyclerWarmStoryAdapter;
import com.liz.mj.bean.WarmStorys;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * description: 温暖的故事
 * author: https://github.com/lizwangying
 * date:  2016/5/23 22:58
 */
public class WarmStoryActivity extends BaseActivity {

    @Bind(R.id.recycler_warm_story)
    RecyclerView recyclerWarmStory;
    
    private RecyclerWarmStoryAdapter adapter;
    private List<WarmStorys> storysLists;
    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_warm_story);
    }

    @Override
    public void initView() {
        recyclerWarmStory.setLayoutManager(new LinearLayoutManager(WarmStoryActivity.this));
    }
//    @OnClick({R.id.image_back})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.image_back:
//                finish();
//                break;
//        }
//    }
    @Override
    public void initData() {
        Log.e("haha", "warm------");

        String bql = "select * from WarmStorys";
        new BmobQuery<WarmStorys>().doSQLQuery(WarmStoryActivity.this, bql, new SQLQueryListener<WarmStorys>() {
            @Override
            public void done(BmobQueryResult<WarmStorys> bmobQueryResult, BmobException e) {
                Log.e("haha", "warm-------1--------");
                if (e == null) {
                    storysLists = bmobQueryResult.getResults();
                    if (storysLists != null && storysLists.size() > 0) {
                        adapter = new RecyclerWarmStoryAdapter(WarmStoryActivity.this, storysLists);
                        recyclerWarmStory.setAdapter(adapter);
                        adapter.setOnItemClickListener(new RecyclerWarmStoryAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(WarmStoryActivity.this, "click" + position, Toast.LENGTH_SHORT).show();
                                WarmStorys story = storysLists.get(position);
                                story.increment("storySeen");
                                story.update(WarmStoryActivity.this, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        showToast("查看次数加1");
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        showToast("查看次数加增加失败");
                                    }
                                });
                                Intent intent = new Intent(WarmStoryActivity.this, StoryDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("WarmStorys", storysLists.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(WarmStoryActivity.this, "long click" + position, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(WarmStoryActivity.this, "怎么回事？没有数据？淡定，让我想想~~~", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WarmStoryActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                    Log.i("haha", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });
    }
}
