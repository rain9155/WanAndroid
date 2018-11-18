package com.example.hy.wanandroid.widget.behaviour;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * 自定义floatingButton的Behaviour：
 * child监听另一个View的滑动状态
 * Created by 陈健宇 at 2018/10/28
 */
public class FbtnBehaviour extends CoordinatorLayout.Behavior<View>{

    public FbtnBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof BottomNavigationView;//这里关心底部导航栏的滑动状态
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        float translationY = dependency.getTranslationY();
        child.setTranslationY(translationY);
        return true;
    }
}
