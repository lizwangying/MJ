package com.liz.mj.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.R;
import com.liz.mj.bean.BannerPicture;

import java.util.List;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-04-26 19:05
 * version:
 */
public class BannerImagePagerAdapter extends PagerAdapter {
    private List<BannerPicture> imagesList;
    private Context context;

    public BannerImagePagerAdapter(Context context, List<BannerPicture> imagesList) {
        this.imagesList = imagesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //是否是同一张照片
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner,container,false);
        String imageUri = imagesList.get(position).getImageResource().getFileUrl(context);
        Uri uri = Uri.parse(imageUri);
        SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.image_banner_drawee);
        draweeView.setImageURI(uri);
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
