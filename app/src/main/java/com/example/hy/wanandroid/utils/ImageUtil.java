package com.example.hy.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.GlideApp;

/**
 * 图片加载工具
 * Created by 陈健宇 at 2018/11/28
 */
public class ImageUtil {

    private ImageUtil() {
        throw new IllegalAccessError();
    }

    public static void loadImage(Context context, ImageView imageView, String path){

        String imageUrl = path;
        DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.AUTOMATIC;
        boolean isSkipMemory = false;

        if(App.getContext().getAppComponent().getDataModel().getNoImageState())
            imageUrl = null;
        if(!App.getContext().getAppComponent().getDataModel().getAutoCacheState()){
            diskCacheStrategy = DiskCacheStrategy.NONE;
            isSkipMemory = true;
        }

            GlideApp.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_android)
                    .error(R.drawable.placeholder_android)
                    .diskCacheStrategy(diskCacheStrategy)
                    .skipMemoryCache(isSkipMemory)
                    .into(imageView);
    }
}
