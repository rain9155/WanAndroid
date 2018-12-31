package com.example.hy.wanandroid.view.mine;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.base.activity.BaseMvpActivity;
import com.example.hy.wanandroid.component.UpdataService;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.mine.SettingsContract;
import com.example.hy.wanandroid.di.component.activity.DaggerSettingsActivityComponent;
import com.example.hy.wanandroid.presenter.mine.SettingsPresenter;
import com.example.hy.wanandroid.utlis.DownloadUtil;
import com.example.commonlib.utils.FileUtil;
import com.example.commonlib.utils.ServiceUtil;
import com.example.commonlib.utils.ShareUtil;
import com.example.commonlib.utils.StatusBarUtil;
import com.example.hy.wanandroid.widget.dialog.ClearCacheDialog;
import com.example.hy.wanandroid.widget.dialog.VersionDialog;

import java.io.File;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import dagger.Lazy;

public class SettingsActivity extends BaseMvpActivity<SettingsPresenter>
        implements SettingsContract.View, CompoundButton.OnCheckedChangeListener {


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
    @BindView(R.id.cd_base_settings)
    CardView cdBaseSettings;
    @BindView(R.id.cd_other_settings)
    CardView cdOtherSettings;
    @BindView(R.id.root_view)
    LinearLayout rootView;
    @BindView(R.id.iv_auto_updata)
    ImageView ivAutoUpdata;
    @BindView(R.id.tv_auto_updata)
    TextView tvAutoUpdata;
    @BindView(R.id.switch_auto_updata)
    SwitchCompat switchAutoUpdata;
    @BindView(R.id.cl_auto_updata)
    ConstraintLayout clAutoUpdata;

    @Inject
    SettingsPresenter mPresenter;
    @Inject
    File mCacheFile;
    @Inject
    Lazy<VersionDialog> mVersionDialog;
    @Inject
    Lazy<ClearCacheDialog> mClearCacheDialog;

    private ObjectAnimator mAnimator;
    private String mNewVersionName;
    private String mCurrentVersionName;

    @Override
    protected void inject() {
        DaggerSettingsActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected SettingsPresenter getPresenter() {
        return mPresenter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
       super.initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setHeightAndPadding(this, tlCommon);
        isEnableTip = false;
        initToolBar();
        initSwitch();
        initSettings();

    }

    private void initSettings() {
        mCurrentVersionName = DownloadUtil.getVersionName(this);
        tvCache.setText(FileUtil.getCacheSize(mCacheFile));
        tvVersion.setText(getString(R.string.settingsActivity_version_current) + mCurrentVersionName);
        clClearCache.setOnClickListener(v -> {
            String cache = FileUtil.getCacheSize(mCacheFile);
            if (cache.equals("0K"))
                showToast(getString(R.string.settingsActivity_already_clear));
            else {
                mClearCacheDialog.get().setContent(cache);
                mClearCacheDialog.get().show(getSupportFragmentManager(), "tag8");
            }
        });
        clFeedBack.setOnClickListener(v -> ShareUtil.sendEmail(this, Constant.EMAIL_ADDRESS, getString(R.string.settingsActivity_email_to)));
        clUpdata.setOnClickListener(v -> {
            if (ServiceUtil.isServiceRunning(this, UpdataService.class.getName())) {
                showToast(getString(R.string.downloading));
                return;
            }
            if (mAnimator != null && mAnimator.isRunning()) return;
            mPresenter.checkVersion(mCurrentVersionName);
        });
    }

    private void initSwitch() {
        switchNoImage.setChecked(mPresenter.getNoImageState());
        switchAutoCache.setChecked(mPresenter.getAutoCacheState());
        switchNightMode.setChecked(mPresenter.getNightModeState());
        switchStatusBar.setChecked(mPresenter.getStatusBarState());
        switchAutoUpdata.setChecked(mPresenter.getAutoUpdataState());
        switchAutoCache.setOnCheckedChangeListener(this);
        switchNoImage.setOnCheckedChangeListener(this);
        switchNightMode.setOnCheckedChangeListener(this);
        switchStatusBar.setOnCheckedChangeListener(this);
        switchAutoUpdata.setOnCheckedChangeListener(this);
    }

    private void initToolBar() {
        //标题栏
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tvCommonTitle.setText(R.string.mineFragment_tvSetting);
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> finish());
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
        if (mAnimator != null) mAnimator.cancel();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(mClearCacheDialog != null)
            mClearCacheDialog = null;
        if(mVersionDialog != null)
            mVersionDialog = null;
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            DownloadUtil.downloadApk(this, mNewVersionName);
        } else {
            showToast(getString(R.string.settingsActivity_permission_denied));
        }
    }

    @Override
    public void useNightNode(boolean isNight) {
        int background, primaryText, foreground, colorPrimary, colorPrimaryDark, colorRipple;
        if (isNight) {
            colorPrimary = Color.parseColor("#212121");
            if (mPresenter.getStatusBarState())
                colorPrimaryDark = Color.parseColor("#212121");
            else
                colorPrimaryDark = Color.parseColor("#424242");
            background = Color.parseColor("#212121");
            foreground = Color.parseColor("#424242");
            primaryText = Color.parseColor("#FAFAFA");
            colorRipple = Color.parseColor("#c7f5f5f5");
        } else {
            colorPrimary = Color.parseColor("#00BCD4");
            if (mPresenter.getStatusBarState())
                colorPrimaryDark = Color.parseColor("#00BCD4");
            else
                colorPrimaryDark = Color.parseColor("#0097A7");
            foreground = Color.parseColor("#FFFFFF");
            background = Color.parseColor("#fafafa");
            primaryText = Color.parseColor("#212121");
            colorRipple = Color.parseColor("#B3E5FC");
        }
        //动态改变颜色
        StatusBarUtil.immersive(this, colorPrimaryDark, 1f);
        tlCommon.setBackgroundColor(colorPrimary);
        rootView.setBackgroundColor(background);
        tvSettingsCommon.setTextColor(primaryText);
        cdBaseSettings.setCardBackgroundColor(foreground);
        tvNoImage.setTextColor(primaryText);
        tvNightMode.setTextColor(primaryText);
        tvAutoCache.setTextColor(primaryText);
        tvStatusBar.setTextColor(primaryText);
        tvAutoUpdata.setTextColor(primaryText);
        tvSettingsOther.setTextColor(primaryText);
        cdOtherSettings.setCardBackgroundColor(foreground);
        tvFeedBack.setTextColor(primaryText);
        tvClearCache.setTextColor(primaryText);
        tvCache.setTextColor(primaryText);
        tvUpdata.setTextColor(primaryText);
        tvVersion.setTextColor(primaryText);
        //动态改变波纹点击颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((RippleDrawable) clNoImage.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
            ((RippleDrawable) clAutoCache.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
            ((RippleDrawable) clClearCache.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
            ((RippleDrawable) clNightMode.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
            ((RippleDrawable) clStatusBar.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
            ((RippleDrawable) clFeedBack.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
            ((RippleDrawable) clUpdata.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
            ((RippleDrawable) clAutoUpdata.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
        }
    }

    public void showChangeAnimation() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
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
        mVersionDialog.get().setContentText(content);
        mVersionDialog.get().setIsMain(false);
        mVersionDialog.get().show(getSupportFragmentManager(), "tag4");
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
    public void clearCache() {
        FileUtil.deleteDir(mCacheFile);
        tvCache.setText(FileUtil.getCacheSize(mCacheFile));
        showToast(getString(R.string.settingsActivity_clear_cache));
    }

    @Override
    public void upDataVersion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constant.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
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

    /**
     * 获取一个View的缓存视图
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }


}

