package com.liz.mj.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.R;
import com.liz.mj.bean.MJAlbums;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-04-25 20:22
 * version:
 */
public class AlbumGridViewAdapter extends BaseAdapter {
    private List<MJAlbums> albumList;
    private Context context;

    public AlbumGridViewAdapter(List<MJAlbums> albumList, Context context) {
        this.albumList = albumList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int i) {
        return albumList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_hot_albums, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MJAlbums albums = albumList.get(position);
        holder.textAlbumName.setText(albums.getAlbumName());
        holder.textAlbumPlayNum.setText(albums.getAlbumPlayNum());
        holder.textAlbumPublishTime.setText(albums.getAlbumPublishedTime());
        holder.textAlbumDescription.setText(albums.getAlbumDescription());
        String imageUri = albumList.get(position).getAlbumCover().getFileUrl(context);
        Uri uri = Uri.parse(imageUri);
        holder.draweeView.setImageURI(uri);
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
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
