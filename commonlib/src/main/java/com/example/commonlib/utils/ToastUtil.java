package com.example.commonlib.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlib.R;

/**
 * Created by 陈健宇 at 2018/12/5
 */
public class ToastUtil{

    private static Toast mToastInBottom;
    private static Toast mToastInBottomForFragment;
    @SuppressLint("StaticFieldLeak")
    private static TextView mTextView;
    private static Toast mShowToast;

    /**
     * Toast提示
     * @param message 的内容
     */
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String message){
        if(mShowToast == null){
            mShowToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            mShowToast.setText(message);
        }
        mShowToast.show();
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
        if(mToastInBottom == null){
            @SuppressLint("InflateParams")
            View toastView = LayoutInflater.from(context).inflate(R.layout.toast_bottom, null);
            mTextView = toastView.findViewById(R.id.tv_toast);
            mTextView.setText(message);

            mToastInBottom = new Toast(context);
            mToastInBottom.setGravity(Gravity.BOTTOM, 0, DisplayUtil.dip2px(context, 50));
            mToastInBottom.setDuration(Toast.LENGTH_SHORT);
            mToastInBottom.setView(toastView);
         }else {
            mTextView.setText(message);
        }
        mToastInBottom.show();
    }

    @SuppressLint({"ResourceAsColor"})
    public static void toastInBottom(Activity activity, String message) {
        if(mToastInBottomForFragment == null){
            @SuppressLint("InflateParams")
            View toastView = LayoutInflater.from(activity).inflate(R.layout.toast_bottom, null);
            mTextView = toastView.findViewById(R.id.tv_toast);
            mTextView.setText(message);

            mToastInBottomForFragment = new Toast(activity);
            mToastInBottomForFragment.setGravity(Gravity.BOTTOM, 0, DisplayUtil.dip2px(activity, 50));
            mToastInBottomForFragment.setDuration(Toast.LENGTH_SHORT);
            mToastInBottomForFragment.setView(toastView);
        }else {
            mTextView.setText(message);
        }
        mToastInBottomForFragment.show();
    }

    public static void toastMake(final TextView textView, final ViewGroup viewGroup, String s, int backgroundColor, int textColor) {
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
