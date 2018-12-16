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
import q.rorbin.badgeview.DisplayUtil;

/**
 * Created by 陈健宇 at 2018/12/5
 */
public class ToastUtil{

    private static Toast toast;
    @SuppressLint("StaticFieldLeak")
    private static TextView textView;
    private static Toast toast2;

    /**
     * Toast提示
     * @param message 的内容
     */
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String message){
        if(toast2 == null){
            toast2 = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            toast2.setText(message);
        }
        toast2.show();
    }

    @SuppressLint({"ResourceAsColor"})
    public static void toastInCenter(Context context, String message) {
        @SuppressLint("InflateParams") View toastView = LayoutInflater.from(context).inflate(R.layout.toast_center, null);
        TextView textView = toastView.findViewById(R.id.tv_toast);
        textView.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(17, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }

    @SuppressLint({"ResourceAsColor"})
    public static void toastInBottom(Context context, String message) {
        if(toast == null){
            @SuppressLint("InflateParams")
            View toastView = LayoutInflater.from(context).inflate(R.layout.toast_bottom, null);
            textView = toastView.findViewById(R.id.tv_toast);
            textView.setText(message);

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
        ObjectAnimator translationY1 = ObjectAnimator.ofFloat(textView, "translationY", -65.0F, 0.0F);
        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(textView, "translationY", 0.0F, -65.0F);
        translationY2.setStartDelay(2500L);
        animatorSet.playSequentially(translationY1, translationY2);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            public void onAnimationEnd(Animator animation) {
                viewGroup.removeView(textView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
