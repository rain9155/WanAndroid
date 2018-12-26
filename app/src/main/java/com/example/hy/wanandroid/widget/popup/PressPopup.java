package com.example.hy.wanandroid.widget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import androidx.core.content.ContextCompat;

/**
 * 长按弹出框
 * Created by 陈健宇 at 2018/12/26
 */
public class PressPopup extends PopupWindow {

    private OnClickListener mClickListener;

    public PressPopup(Context context) {
        super(context);
        initPopup(context);
    }

    public void show(View view, int x, int y) {
        this.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.mClickListener = onClickListener;
    }

    private void initPopup(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_press, null);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnim);
        this.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.background)));
        TextView tvShare = view.findViewById(R.id.tv_share);
        tvShare.setOnClickListener(v -> {
            if (mClickListener == null) return;
            mClickListener.onShareClick();
        });
        TextView tvOpenBrowse = view.findViewById(R.id.tv_open_browse);
        tvOpenBrowse.setOnClickListener(v -> {
            if (mClickListener == null) return;
            mClickListener.onOpenBrowserClick();
        });
        TextView tvCopy = view.findViewById(R.id.tv_copy);
        tvCopy.setOnClickListener(v -> {
            if(mClickListener == null) return;
            mClickListener.onCopyClick();
        });
    }

    interface OnClickListener {
        void onShareClick();
        void onOpenBrowserClick();
        void onCopyClick();
    }
}
