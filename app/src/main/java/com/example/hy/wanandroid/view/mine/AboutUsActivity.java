package com.example.hy.wanandroid.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.hy.wanandroid.R;

import javax.inject.Inject;

public class AboutUsActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }
}
