package com.example.hy.wanandroid.utlis;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hy.wanandroid.R;

/**
 * Created by 陈健宇 at 2018/12/5
 */
public class ToastUtil{

    /**
     * Toast提示
     * @param message 的内容
     */
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showCustomToast(Context context, View contentView, Pos pos) {
        Toast toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(pos.gravity, pos.xOffset, pos.yOffset);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showCustomToastInCenter(Context context, String message) {
        View toastView = LayoutInflater.from(context).inflate(R.layout.toast_center, null);
        TextView textView = toastView.findViewById(R.id.tv_toast);
        textView.setText(message);
        Pos pos = new Pos(17, 0, 0);
        showCustomToast(context, toastView, pos);
    }

    public static void showCustomToastInBottom(Context context, String message) {
        View toastView = LayoutInflater.from(context).inflate(R.layout.toast_bottom, null);
        TextView textView = toastView.findViewById(R.id.tv_toast);
        textView.setText(message);
        Pos pos = new Pos(Gravity.BOTTOM, 0, DisplayUtil.dip2px(context, 50));
        showCustomToast(context, toastView, pos);
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

            @Override
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

    private static final class Pos {

        public int gravity;

        public int xOffset;

        public int yOffset;

        public Pos(int gravity, int xOffset, int yOffset) {
            this.gravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
    }
}
