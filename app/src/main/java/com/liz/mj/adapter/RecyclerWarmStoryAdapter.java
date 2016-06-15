package com.liz.mj.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.R;
import com.liz.mj.bean.WarmStorys;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description:哇塞，第一次使用RecyclerView，实现平常的ListView的效果之外，加上牛逼的局部刷新
 * author: lizwangying@icloud.com
 * date: 2016-05-11 16:15
 */
public class RecyclerWarmStoryAdapter extends RecyclerView.Adapter<RecyclerWarmStoryAdapter.MyViewHolder> {
    private Context context;
    private List<WarmStorys> storysList;

    public RecyclerWarmStoryAdapter(Context context, List<WarmStorys> storysList) {
        this.context = context;
        this.storysList = storysList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item_warm_story, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        holder.textSongName.setText(songsList.get(position).getSongName());
//        holder.textSongAlbum.setText(songsList.get(position).getAlbumNameString());
//        holder.coverImage.
        String imageUri = storysList.get(position).getStoryIcon().getFileUrl(context);
        Uri uri = Uri.parse(imageUri);
        holder.coverImage.setImageURI(uri);
        holder.textStoryTitle.setText(storysList.get(position).getStoryTitle());
        holder.textLike.setText(storysList.get(position).getStoryLiked()+"");
        holder.textSeen.setText(storysList.get(position).getStorySeen()+"");

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

    @Override
    public int getItemCount() {
        return storysList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Bind(R.id.text_story_title)
        TextView textStoryTitle;
        @Bind(R.id.text_seen)
        TextView textSeen;
        @Bind(R.id.text_like)
        TextView textLike;
        @Bind(R.id.image_item_hot_topic)
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
