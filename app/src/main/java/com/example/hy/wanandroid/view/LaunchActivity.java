package com.example.hy.wanandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.widget.customView.SVGBgView;

import java.io.ObjectInputStream;

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
