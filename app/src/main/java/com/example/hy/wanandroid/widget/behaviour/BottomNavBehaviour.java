package com.example.hy.wanandroid.widget.behaviour;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.hy.wanandroid.utils.LogUtil;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * 自定义底部导航栏Behaviour,：
 * child监听CoordinatorLayout的滑动状态
 * Created by 陈健宇 at 2018/10/27
 */
public class BottomNavBehaviour extends CoordinatorLayout.Behavior<View>{

    private int mDirectionY;
    private ObjectAnimator mHideAnimator, mShowAnimator;

    public BottomNavBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;//表示只接受垂直方向的滑动
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //dy是我们手指滑动的垂直方向的值，child为设置了此Behaviour的View
        if(dy > 0){//向上滑为正值, 隐藏
            hide(child);
        }else if(dx < 0){//向下滑为负值，显示
            show(child);
        }
    }

    private void show(View child) {
        if(mShowAnimator == null){
            mShowAnimator = ObjectAnimator.ofFloat(child, "translationY", child.getHeight(), 0)
                    .setDuration(200);
            mShowAnimator.setInterpolator(new FastOutSlowInInterpolator());
        }
        if(!mShowAnimator.isRunning() && child.getTranslationY() >= child.getHeight()){
            mShowAnimator.start();
        }
    }

    private void hide(View child) {
        if(mHideAnimator == null){
            mHideAnimator =  ObjectAnimator.ofFloat(child, "translationY", 0, child.getHeight())
                    .setDuration(300);
            mHideAnimator.setInterpolator(new FastOutSlowInInterpolator());
        }
        if(!mHideAnimator.isRunning() && child.getTranslationY() <= 0){
            mHideAnimator.start();
        }
    }

}
