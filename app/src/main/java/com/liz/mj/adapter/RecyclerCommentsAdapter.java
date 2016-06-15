package com.liz.mj.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.R;
import com.liz.mj.bean.MyUser;
import com.liz.mj.bean.TopicComments;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * description:哇塞，第一次使用RecyclerView，实现平常的ListView的效果之外，加上牛逼的局部刷新
 * author: lizwangying@icloud.com
 * date: 2016-05-11 16:15
 */
public class RecyclerCommentsAdapter extends RecyclerView.Adapter<RecyclerCommentsAdapter.MyViewHolder> {
    private Context context;
    private List<TopicComments> commentsList;

    public RecyclerCommentsAdapter(Context context, List<TopicComments> commentsList) {
        this.context = context;
        this.commentsList = commentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item_comment, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.textComment.setText(commentsList.get(position).getComments());
        String userId = commentsList.get(position).getCommentAuthor().getObjectId();
//        holder.textCommentAuthor.setText();
//        holder.coverImage.
        holder.textCommentTime.setText(commentsList.get(position).getCreatedAt());

        //查询并设置用户的用户名，头像
        queryUserInfo(holder,userId);

        //如果设置了点击回调，则设置点击事件
        if (myOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    myOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    myOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }

    }
    void queryUserInfo(final MyViewHolder holder, String userId){
        BmobQuery<MyUser> myUserBmobQuery = new BmobQuery<>();
        myUserBmobQuery.addWhereEqualTo("objectId",userId);
        myUserBmobQuery.addQueryKeys("username,userIcon");
        myUserBmobQuery.findObjects(context, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                holder.textCommentAuthor.setText(list.get(0).getUsername());
                try {
                    holder.coverImage.setImageURI(Uri.parse(list.get(0).getUserIcon().getFileUrl(context)));
                }catch (NullPointerException e){

                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("haha", "onSuccess: 查询评论用户lose");

            }
        });
    }
    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Bind(R.id.text_comment_time)
        TextView textCommentTime;
        @Bind(R.id.text_comment)
        TextView textComment;
        @Bind(R.id.text_comment_author)
        TextView textCommentAuthor;
        @Bind(R.id.image_video_cover)
        SimpleDraweeView coverImage;

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener myOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.myOnItemClickListener = onItemClickListener;
    }

//    AsyncTask
}
