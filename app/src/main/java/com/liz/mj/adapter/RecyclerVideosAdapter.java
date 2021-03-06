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
import com.liz.mj.bean.MJVideos;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description:哇塞，第一次使用RecyclerView，实现平常的ListView的效果之外，加上牛逼的局部刷新
 * author: lizwangying@icloud.com
 * date: 2016-05-11 16:15
 */
public class RecyclerVideosAdapter extends RecyclerView.Adapter<RecyclerVideosAdapter.MyViewHolder> {
    private Context context;
    private List<MJVideos> videoList;

    public RecyclerVideosAdapter(Context context, List<MJVideos> videoList) {
        this.context = context;
        this.videoList = videoList;
        Log.e("haha","constru-----"+videoList.size()+"2222"+videoList.get(0).getVideoName());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item_video,parent,false));
        Log.e("haha","create adapter");
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Log.e("haha","bind--------"+videoList.get(0).getVideoName());
        holder.textVideoName.setText("dfffff");
//        holder.textVideoName.setText(videoList.get(position).getVideoName());
        String imageUri = videoList.get(position).getVideoCover().getFileUrl(context);
        Uri uri = Uri.parse(imageUri);
        holder.coverImage.setImageURI(uri);
        //如果设置了点击回调，则设置点击事件
        if (myOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    myOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int pos = holder.getLayoutPosition();
                myOnItemClickListener.onItemLongClick(holder.itemView, pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Bind(R.id.text_video_name)
        TextView textVideoName;
        @Bind(R.id.image_video_cover)
        SimpleDraweeView coverImage;

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener myOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.myOnItemClickListener = onItemClickListener;
    }

//    AsyncTask
}
