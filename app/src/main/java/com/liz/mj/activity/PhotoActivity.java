package com.liz.mj.activity;

import android.content.Intent;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.bean.Album;

import butterknife.Bind;

public class PhotoActivity extends BaseActivity {

    @Bind(R.id.photo_image)
    SimpleDraweeView photo;
    private Album album;
    private String imageUri;
    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_photo2);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        album = (Album) intent.getSerializableExtra("Album");
        imageUri = album.getPictures().getFileUrl(PhotoActivity.this);
        Uri uri = Uri.parse(imageUri);
        photo.setImageURI(uri);
    }
}
