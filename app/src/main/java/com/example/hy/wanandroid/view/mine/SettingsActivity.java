package com.example.hy.wanandroid.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.mine.SettingsContract;
import com.example.hy.wanandroid.di.component.activity.DaggerSettingsActivityComponent;
import com.example.hy.wanandroid.presenter.mine.SettingsPresenter;
import com.example.hy.wanandroid.utils.ShareUtil;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;
import com.example.hy.wanandroid.view.search.SearchActivity;

import javax.inject.Inject;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity implements SettingsContract.View, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.tv_settings_common)
    TextView tvSettingsCommon;
    @BindView(R.id.iv_noImage)
    ImageView ivNoImage;
    @BindView(R.id.tv_noImage)
    TextView tvNoImage;
    @BindView(R.id.switch_noImage)
    SwitchCompat switchNoImage;
    @BindView(R.id.cl_noImage)
    ConstraintLayout clNoImage;
    @BindView(R.id.iv_autoCache)
    ImageView ivAutoCache;
    @BindView(R.id.tv_autoCache)
    TextView tvAutoCache;
    @BindView(R.id.switch_autoCache)
    SwitchCompat switchAutoCache;
    @BindView(R.id.cl_autoCache)
    ConstraintLayout clAutoCache;
    @BindView(R.id.iv_nightMode)
    ImageView ivNightMode;
    @BindView(R.id.tv_nightMode)
    TextView tvNightMode;
    @BindView(R.id.switch_nightMode)
    SwitchCompat switchNightMode;
    @BindView(R.id.cl_nightMode)
    ConstraintLayout clNightMode;
    @BindView(R.id.tv_settings_other)
    TextView tvSettingsOther;
    @BindView(R.id.iv_feedBack)
    ImageView ivFeedBack;
    @BindView(R.id.tv_feedBack)
    TextView tvFeedBack;
    @BindView(R.id.cl_feedBack)
    ConstraintLayout clFeedBack;
    @BindView(R.id.iv_clearCache)
    ImageView ivClearCache;
    @BindView(R.id.tv_clearCache)
    TextView tvClearCache;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.cl_clearCache)
    ConstraintLayout clClearCache;

    @Inject
    SettingsPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView( ) {
        DaggerSettingsActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
        mPresenter.attachView(this);

        //标题栏
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tvCommonTitle.setText(R.string.mineFragment_tvSetting);
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> finish());

        switchNoImage.setChecked(mPresenter.getNoImageState());
        switchAutoCache.setChecked(mPresenter.getAutoCacheState());
        switchNightMode.setChecked(mPresenter.getNightModeState());

        clFeedBack.setOnClickListener(v -> ShareUtil.sendEmail(this, Constant.EMAIL_ADDRESS, getString(R.string.settingsActivity_email_to)));

        switchAutoCache.setOnCheckedChangeListener(this);
        switchNoImage.setOnCheckedChangeListener(this);
        switchNightMode.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_autoCache:
                mPresenter.setAutoCacheState(isChecked);
                break;
            case R.id.switch_noImage:
                mPresenter.setNoImageState(isChecked);
                break;
            case R.id.switch_nightMode:
                mPresenter.setNightModeState(isChecked);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
}
