package com.example.hy.wanandroid.widget.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hy.wanandroid.entity.Collection;
import com.example.hy.wanandroid.utlis.ShareUtil;
import com.example.hy.wanandroid.utlis.ToastUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.entity.Article;

import androidx.core.content.ContextCompat;

import javax.inject.Inject;

/**
 * 长按弹出框，PopupWindow实现
 * Created by 陈健宇 at 2018/12/26
 */
public class PressPopupWindow extends PopupWindow {

    private OnClickListener mClickListener;
    private String mTitle;
    private String mLink;
    private boolean isPress;

    @Inject
    public PressPopupWindow(Context context) {
        super(context);
        mTitle = "";
        mLink = "";
    }

    public void show(View parentView, View view, Article article) {
        show(parentView, view, article.getTitle(), article.getLink());
    }

    public void show(View parentView, View view, Collection collection) {
        show(parentView, view, collection.getTitle(), collection.getLink());
    }

    public void show(View parentView, View view, String title, String link) {
        initPopup(view.getContext());
        view.setOnTouchListener((v, event) -> {
            boolean isShow = (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) && isPress;
            if(isShow){
                this.showAtLocation(parentView, Gravity.NO_GRAVITY, (int) event.getRawX(), (int) event.getRawY());
                this.mTitle = title;
                this.mLink = link;
                isPress = false;
            }
            return false;
        });
        isPress = true;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.mClickListener = onClickListener;
    }

    private void initPopup(Context context) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.popup_long_press, null);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(view);
        this.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_popup));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnim);
        TextView tvShare = view.findViewById(R.id.tv_share);
        tvShare.setOnClickListener(v -> {
            ShareUtil.shareText(
                    context,
                    context.getString(R.string.articleActivity_share_text) + "\n" + mTitle + "\n" + mLink,
                    context.getString(R.string.articleActivity_share_to)
            );
            if (mClickListener != null) {
                mClickListener.onShareClick();
            }
            this.dismiss();
        });
        TextView tvOpenBrowse = view.findViewById(R.id.tv_open);
        tvOpenBrowse.setOnClickListener(v -> {
            ShareUtil.openBrowser(context, mLink);
            if (mClickListener != null) {
                mClickListener.onOpenBrowserClick();
            }
            this.dismiss();
        });
        TextView tvCopy = view.findViewById(R.id.tv_copy);
        tvCopy.setOnClickListener(v -> {
            ShareUtil.copyString(context, mLink);
            ToastUtil.showCustomToastInBottom(context, context.getString(R.string.articleActivity_copy_success));
            if(mClickListener != null) {
                mClickListener.onCopyClick();
            }
            this.dismiss();
        });
    }

    public interface OnClickListener {
        void onShareClick();
        void onOpenBrowserClick();
        void onCopyClick();
    }
}
