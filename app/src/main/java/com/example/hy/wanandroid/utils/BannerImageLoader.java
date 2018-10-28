package com.example.hy.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.GlideApp;
import com.youth.banner.loader.ImageLoader;

/**
 * 为首页banner展示图片指定图片加载器
 * Created by 陈健宇 at 2018/10/22
 */
public class BannerImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideApp.with(context)
                .load(path)
                .placeholder(R.drawable.placeholder_android)
                .error(R.drawable.placeholder_android)
                .into(imageView);
    }
}
