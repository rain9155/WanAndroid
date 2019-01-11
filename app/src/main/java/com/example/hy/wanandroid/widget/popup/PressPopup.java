package com.example.hy.wanandroid.widget.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.commonlib.utils.ShareUtil;
import com.example.commonlib.utils.ToastUtil;
import com.example.hy.wanandroid.R;

import androidx.core.content.ContextCompat;

/**
 * 长按弹出框
 * Created by 陈健宇 at 2018/12/26
 */
public class PressPopup extends PopupWindow {

    private OnClickListener mClickListener;
    private String mTitle;
    private String mLink;
    private Context mContext;

    public PressPopup(Context context) {
        super(context);
        initPopup(context);
        mTitle = "";
        mLink = "";
    }

    public void show(View view, float x, float y) {
        this.showAtLocation(view, Gravity.NO_GRAVITY, (int) x, (int) y);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.mClickListener = onClickListener;
    }

    public void setMessage(String title, String link){
        this.mTitle = title;
        this.mLink = link;
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
            if (mClickListener != null)
                mClickListener.onShareClick();
            this.dismiss();
        });
        TextView tvOpenBrowse = view.findViewById(R.id.tv_open_browse);
        tvOpenBrowse.setOnClickListener(v -> {
            ShareUtil.openBrowser(context, mLink);
            if (mClickListener != null)
                mClickListener.onOpenBrowserClick();
            this.dismiss();
        });
        TextView tvCopy = view.findViewById(R.id.tv_copy);
        tvCopy.setOnClickListener(v -> {
            ShareUtil.copyString(context, mLink);
            ToastUtil.toastInBottom(context, context.getString(R.string.articleActivity_copy_success));
            if(mClickListener != null)
                mClickListener.onCopyClick();
            this.dismiss();
        });
    }

    public interface OnClickListener {
        void onShareClick();
        void onOpenBrowserClick();
        void onCopyClick();
    }
}
