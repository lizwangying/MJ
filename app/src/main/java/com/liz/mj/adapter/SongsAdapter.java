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
import com.liz.mj.bean.MJSongs;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-05-11 13:11
 * version:
 */
public class SongsAdapter extends BaseAdapter {
    private Context context;
    private List<MJSongs> songsList;

    public SongsAdapter(Context context, List<MJSongs> songsList) {
        this.context = context;
        this.songsList = songsList;
    }

    @Override
    public int getCount() {
        return songsList.size();
    }

    @Override
    public Object getItem(int i) {
        return songsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_songs, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MJSongs songs = songsList.get(position);
        holder.textSongName.setText(songs.getSongName());
        holder.textSongAlbum.setText(songs.getAlbumId());
        String imageUri = songsList.get(position).getSongFile().getFileUrl(context);
        Uri uri = Uri.parse(imageUri);
        holder.imageAlbumCover.setImageURI(uri);
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.image_album_cover)
        SimpleDraweeView imageAlbumCover;
        @Bind(R.id.text_song_name)
        TextView textSongName;
        @Bind(R.id.text_song_album)
        TextView textSongAlbum;
    }
}
