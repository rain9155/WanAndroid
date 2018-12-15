package com.example.hy.wanandroid.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hy.wanandroid.R;
import com.example.utilslibrary.anim.AnimatorListener;

import q.rorbin.badgeview.DisplayUtil;

/**
 * Created by 陈健宇 at 2018/12/5
 */
public class ToastUtil extends com.example.utilslibrary.ToastUtil {

    private static Toast toast;
    private static TextView textView;

    @SuppressLint({"ResourceAsColor"})
    public static void toastInBottom(Context context, String message, View toastView) {
        if(toast == null){
            if (toastView == null) {
                toastView = LayoutInflater.from(context).inflate(R.layout.toast, null);
                textView = toastView.findViewById(R.id.tv_toast);
                textView.setText(message);
            }
            toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM, 0, DisplayUtil.dp2px(context, 50));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(toastView);
         }else {
            textView.setText(message);
        }
        toast.show();
    }

    public static void toastMake(TextView textView, final ViewGroup viewGroup, String s, int backgroundColor, int textColor) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -2);
        textView.setText(s);
        textView.setGravity(17);
        textView.setPadding(0, 15, 0, 15);
        textView.setTextColor(textColor);
        textView.setBackgroundColor(backgroundColor);
        textView.setLayoutParams(params);
        viewGroup.addView(textView);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translationY1 = ObjectAnimator.ofFloat(textView, "translationY", new float[]{-65.0F, 0.0F});
        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(textView, "translationY", new float[]{0.0F, -65.0F});
        translationY2.setStartDelay(2500L);
        animatorSet.playSequentially(new Animator[]{translationY1, translationY2});
        animatorSet.start();
        animatorSet.addListener(new AnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                viewGroup.removeView(textView);
            }
        });
    }

}
