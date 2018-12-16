package com.example.hy.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;
import com.youth.banner.loader.ImageLoader;

/**
 * 为首页banner展示图片指定图片加载器
 * Created by 陈健宇 at 2018/10/22
 */
public class BannerImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageUtil.loadImage(context, imageView, (String)path);
    }
}
