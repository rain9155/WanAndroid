package com.example.hy.wanandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.widget.customView.SVGBgView;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(
                App.getContext().getAppComponent()
                        .getDataModel()
                        .getNightModeState()
                        ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        SVGBgView svgBgView = findViewById(R.id.iv_launch);
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(svgBgView, "alpha", 0, 1f)
                .setDuration(2800);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.anim_launch_enter, 0);
            finish();
        }, 3000);
    }
}
