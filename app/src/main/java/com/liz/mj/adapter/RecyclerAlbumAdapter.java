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
import com.liz.mj.bean.MJAlbums;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description:专辑页面的->专辑Adapter
 * author: lizwangying@icloud.com
 * date: 2016-05-11 16:50
 * version:
 */
public class RecyclerAlbumAdapter extends RecyclerView.Adapter<RecyclerAlbumAdapter.MyViewHolder> {
    private Context context;
    private List<MJAlbums> albumsList;

    public RecyclerAlbumAdapter(List<MJAlbums> albumsList, Context context) {
        this.albumsList = albumsList;
        this.context = context;
    }

    @Override
    public RecyclerAlbumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.grid_view_hot_albums,parent,false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAlbumAdapter.MyViewHolder holder, int position) {
        if (albumsList.size()>0) {
            MJAlbums albums = albumsList.get(position);
            holder.textAlbumName.setText(albums.getAlbumName());
            holder.textAlbumPlayNum.setText(albums.getAlbumPlayNum());
            holder.textAlbumPublishTime.setText(albums.getAlbumPublishedTime());
            holder.textAlbumDescription.setText(albums.getAlbumDescription());
            String imageUri = albumsList.get(position).getAlbumCover().getFileUrl(context);
            Uri uri = Uri.parse(imageUri);
            holder.draweeView.setImageURI(uri);

        }else {
            Log.e("haha","albums数据没有~");
        }
    }

    @Override
    public int getItemCount() {
        return albumsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            textAlbumName = (TextView) itemView.findViewById(R.id.text_album_name);
        }
        @Bind(R.id.text_album_name)
        TextView textAlbumName;
        @Bind(R.id.text_album_play_num)
        TextView textAlbumPlayNum;
        @Bind(R.id.text_album_published_time)
        TextView textAlbumPublishTime;
        @Bind(R.id.image_album_cover)
        SimpleDraweeView draweeView;
        @Bind(R.id.text_album_description)
        TextView textAlbumDescription;

    }
}
