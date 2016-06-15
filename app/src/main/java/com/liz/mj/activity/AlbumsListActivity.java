package com.liz.mj.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.adapter.RecyclerSongsAdapter;
import com.liz.mj.bean.MJAlbums;
import com.liz.mj.bean.MJSongs;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class AlbumsListActivity extends BaseActivity {
    @Bind(R.id.recycler_view_albums)
    RecyclerView recyclerAlbums;
    @Bind(R.id.image_album_cover)
    SimpleDraweeView cover;
    @Bind(R.id.text_album_name)
    TextView albumName;
    @Bind(R.id.text_album_published_time)
    TextView albumTime;
    @Bind(R.id.text_album_des)
    TextView albumDes;

    private MJAlbums albums;
    private RecyclerSongsAdapter songsAdapter;
    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_albums_list);
    }


    @Override
    public void initView() {
        Intent intent = getIntent();
        albums = (MJAlbums) intent.getSerializableExtra("MJAlbum");
        cover.setImageURI(Uri.parse(albums.getAlbumCover().getFileUrl(AlbumsListActivity.this)));
        albumName.setText(albums.getAlbumName());
        albumTime.setText(albums.getAlbumPublishedTime());
        albumDes.setText(albums.getAlbumDescription());
        String imageUri = albums.getAlbumCover().getFileUrl(AlbumsListActivity.this);
        Uri uri = Uri.parse(imageUri);
        cover.setImageURI(uri);

    }

    @Override
    public void initData() {
        BmobQuery<MJSongs> query = new BmobQuery<>();
        query.addWhereEqualTo("MJAlbums",albums);
        query.findObjects(AlbumsListActivity.this, new FindListener<MJSongs>() {
            @Override
            public void onSuccess(final List<MJSongs> list) {
                if (list != null && list.size() > 0) {
                    songsAdapter = new RecyclerSongsAdapter(AlbumsListActivity.this, list);
                    recyclerAlbums.setAdapter(songsAdapter);
                    songsAdapter.setOnItemClickListener(new RecyclerSongsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(AlbumsListActivity.this, MusicPlayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MJSongs", list.get(position));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        @Override
                        public void onItemLongClick(View view, int position) {
                            Toast.makeText(AlbumsListActivity.this, "long click" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AlbumsListActivity.this, "怎么回事？没有数据？淡定，让我想想~~~", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
