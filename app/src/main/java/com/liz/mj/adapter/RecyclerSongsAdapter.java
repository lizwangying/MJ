package com.liz.mj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liz.mj.R;
import com.liz.mj.bean.MJSongs;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description:哇塞，第一次使用RecyclerView，实现平常的ListView的效果之外，加上牛逼的局部刷新
 * author: lizwangying@icloud.com
 * date: 2016-05-11 16:15
 */
public class RecyclerSongsAdapter extends RecyclerView.Adapter<RecyclerSongsAdapter.MyViewHolder> {
    private Context context;
    private List<MJSongs> songsList;

    public RecyclerSongsAdapter(Context context, List<MJSongs> songsList) {
        this.context = context;
        this.songsList = songsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item_songs,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textSongName.setText(songsList.get(position).getSongName());
        holder.textSongAlbum.setText(songsList.get(position).getAlbumNameString());
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Bind(R.id.text_song_name)
        TextView textSongName;
        @Bind(R.id.text_song_album)
        TextView textSongAlbum;


    }
}
