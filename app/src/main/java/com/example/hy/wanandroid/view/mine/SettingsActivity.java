package com.example.hy.wanandroid.view.mine;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.component.UpdataService;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.mine.SettingsContract;
import com.example.hy.wanandroid.di.component.activity.DaggerSettingsActivityComponent;
import com.example.hy.wanandroid.presenter.mine.SettingsPresenter;
import com.example.hy.wanandroid.utils.DownloadUtil;
import com.example.hy.wanandroid.utils.FileUtil;
import com.example.hy.wanandroid.utils.LogUtil;
import com.example.hy.wanandroid.utils.ServiceUtil;
import com.example.hy.wanandroid.utils.ShareUtil;
import com.example.hy.wanandroid.utils.StatusBarUtil;
import com.example.hy.wanandroid.widget.dialog.VersionDialog;

import java.io.File;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;

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
    @BindView(R.id.iv_status_bar)
    ImageView ivStatusBar;
    @BindView(R.id.tv_status_bar)
    TextView tvStatusBar;
    @BindView(R.id.switch_status_bar)
    SwitchCompat switchStatusBar;
    @BindView(R.id.cl_status_bar)
    ConstraintLayout clStatusBar;
    @BindView(R.id.iv_updata)
    ImageView ivUpdata;
    @BindView(R.id.tv_updata)
    TextView tvUpdata;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.cl_updata)
    ConstraintLayout clUpdata;

    @Inject
    SettingsPresenter mPresenter;
    @Inject
    File mCacheFile;
    @Inject
    VersionDialog mVersionDialog;

    private ObjectAnimator mAnimator;
    private String mNewVersionName;
    private String mCurrentVersionName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        DaggerSettingsActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
        mPresenter.attachView(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setHeightAndPadding(this, tlCommon);

        //标题栏
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tvCommonTitle.setText(R.string.mineFragment_tvSetting);
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> finish());

        switchNoImage.setChecked(mPresenter.getNoImageState());
        switchAutoCache.setChecked(mPresenter.getAutoCacheState());
        switchNightMode.setChecked(mPresenter.getNightModeState());
        switchStatusBar.setChecked(mPresenter.getStatusBarState());

        mCurrentVersionName = DownloadUtil.getVersionName(this);
        tvCache.setText(FileUtil.getCacheSize(mCacheFile));
        tvVersion.setText(getString(R.string.settingsActivity_version_current) + mCurrentVersionName);

        clClearCache.setOnClickListener(v -> {
            FileUtil.deleteDir(mCacheFile);
            tvCache.setText(FileUtil.getCacheSize(mCacheFile));
            showToast(getString(R.string.settingsActivity_clear_cache));
        });
        clFeedBack.setOnClickListener(v -> ShareUtil.sendEmail(this, Constant.EMAIL_ADDRESS, getString(R.string.settingsActivity_email_to)));
        clUpdata.setOnClickListener(v -> {
            if(ServiceUtil.isServiceRunning(this, UpdataService.class.getName())){
                showToast(getString(R.string.downloading));
                return;
            }
            if(mAnimator != null && mAnimator.isRunning()) return;
            mPresenter.checkVersion(mCurrentVersionName);
        });

        switchAutoCache.setOnCheckedChangeListener(this);
        switchNoImage.setOnCheckedChangeListener(this);
        switchNightMode.setOnCheckedChangeListener(this);
        switchStatusBar.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        mPresenter.subscribleEvent();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_autoCache:
                mPresenter.setAutoCacheState(isChecked);
                break;
            case R.id.switch_noImage:
                mPresenter.setNoImageState(isChecked);
                break;
            case R.id.switch_nightMode:
                mPresenter.setNightModeState(isChecked);
                break;
            case R.id.switch_status_bar:
                mPresenter.setStatusBarState(isChecked);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        if(mAnimator != null) mAnimator.cancel();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Constant.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            DownloadUtil.downloadApk(this, mNewVersionName);
        }else {
            showToast(getString(R.string.settingsActivity_permission_denied));
        }
    }

    @Override
    public void showLoading() {
        startloadAnimator();
    }

    @Override
    public void showErrorView() {
        mAnimator.cancel();
    }

    @Override
    public void showNormalView() {
        mAnimator.cancel();
    }

    @Override
    public void showUpdataDialog(String content) {
        mVersionDialog.setContentText(content);
        mVersionDialog.setIsMain(false);
        mVersionDialog.show(getSupportFragmentManager(), "tag4");
    }

    @Override
    public void setNewVersionName(String versionName) {
        mNewVersionName = versionName;
    }

    @Override
    public void showAlareadNewToast(String content) {
        mAnimator.cancel();
        showToast(content);
    }

    @Override
    public void upDataVersion() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constant.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        }else {
            DownloadUtil.downloadApk(this, mNewVersionName);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 启动load动画
     */
    @SuppressLint("WrongConstant")
    private void startloadAnimator() {
        mAnimator = ObjectAnimator.ofFloat(ivUpdata, "rotation", 0, 360).setDuration(600);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.INFINITE);
        mAnimator.start();
    }

}
