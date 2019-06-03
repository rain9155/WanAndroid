package com.example.hy.wanandroid.view.mine;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.commonlib.utils.LanguageUtil;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.commonlib.utils.FileUtil;
import com.example.commonlib.utils.ServiceUtil;
import com.example.commonlib.utils.ShareUtil;
import com.example.commonlib.utils.StatusBarUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseMvpActivity;
import com.example.permission.bean.Permission;
import com.example.hy.wanandroid.component.UpdataService;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.mine.SettingsContract;
import com.example.hy.wanandroid.di.component.activity.DaggerSettingsActivityComponent;
import com.example.hy.wanandroid.presenter.mine.SettingsPresenter;
import com.example.hy.wanandroid.utlis.DownloadUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.widget.dialog.ClearCacheDialog;
import com.example.hy.wanandroid.widget.dialog.GotoDetialDialog;
import com.example.hy.wanandroid.widget.dialog.LanguageDialog;
import com.example.hy.wanandroid.widget.dialog.VersionDialog;
import com.example.permission.PermissionFragment;
import com.example.permission.PermissionHelper;
import com.example.permission.callback.IPermissionCallback;

import java.io.File;

import javax.inject.Inject;

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
    @BindView(R.id.tv_settings_base)
    TextView tvSettingsBase;
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
    @BindView(R.id.iv_mes)
    ImageView ivMes;
    @BindView(R.id.tv_mes)
    TextView tvMes;
    @BindView(R.id.cl_mes)
    ConstraintLayout clMes;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.tv_language_hint)
    TextView tvLanguageHint;
    @BindView(R.id.cd_common_settings)
    CardView cdCommonSettings;
    @BindView(R.id.cl_language)
    ConstraintLayout clLanguage;

    @Inject
    SettingsPresenter mPresenter;
    @Inject
    File mCacheFile;
    @Inject
    Lazy<VersionDialog> mVersionDialog;
    @Inject
    Lazy<ClearCacheDialog> mClearCacheDialog;
    @Inject
    Lazy<GotoDetialDialog> mGotoDetialDialog;
    @Inject
    Lazy<LanguageDialog> mLanguageDialog;


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
        tvVersion.setText(getString(R.string.settingsActivity_version_current) + mCurrentVersionName);

        clClearCache.setOnClickListener(v -> {
            String cache = FileUtil.getCacheSize(mCacheFile);
            if (cache.equals("0K"))
                showToast(getString(R.string.settingsActivity_already_clear));
            else {
                mClearCacheDialog.get().setContent(cache);
                mClearCacheDialog.get().show(getSupportFragmentManager(), ClearCacheDialog.class.getName());
            }
        });
        tvCache.setText(FileUtil.getCacheSize(mCacheFile));

        clLanguage.setOnClickListener(v -> mLanguageDialog.get().show(getSupportFragmentManager(), LanguageDialog.class.getName()));
        tvLanguageHint.setText(getLanguageHint(mPresenter.getSelectedLanguage()));

        clFeedBack.setOnClickListener(v -> ShareUtil.sendEmail(this, Constant.EMAIL_ADDRESS, getString(R.string.settingsActivity_email_to)));

        clMes.setOnClickListener(v -> ShareUtil.gotoAppDetailIntent(this));

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
        if (mClearCacheDialog.get() != null)
            mClearCacheDialog = null;
        if (mVersionDialog.get() != null)
            mVersionDialog = null;
        if (mGotoDetialDialog.get() != null)
            mGotoDetialDialog = null;
        if(mLanguageDialog.get() != null)
            mLanguageDialog = null;
        super.onDestroy();
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
        tvSettingsBase.setTextColor(primaryText);

        //base settings
        cdBaseSettings.setCardBackgroundColor(foreground);
        tvNoImage.setTextColor(primaryText);
        tvNightMode.setTextColor(primaryText);
        tvAutoCache.setTextColor(primaryText);
        tvStatusBar.setTextColor(primaryText);
        tvAutoUpdata.setTextColor(primaryText);
        tvSettingsOther.setTextColor(primaryText);

        //common settings
        cdCommonSettings.setCardBackgroundColor(foreground);
        tvClearCache.setTextColor(primaryText);
        tvCache.setTextColor(primaryText);
        tvMes.setTextColor(primaryText);
        tvLanguage.setTextColor(primaryText);
        tvLanguageHint.setTextColor(primaryText);

        //other settings
        cdOtherSettings.setCardBackgroundColor(foreground);
        tvFeedBack.setTextColor(primaryText);
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
            ((RippleDrawable) clMes.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
            ((RippleDrawable) clLanguage.getBackground()).setColor(ColorStateList.valueOf(colorRipple));
        }
    }

    @Override
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
        mVersionDialog.get().show(getSupportFragmentManager(), VersionDialog.class.getName());
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
    public void hadleLanguage() {
        finish();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }, 200);

    }

    @Override
    public void clearCache() {
        FileUtil.deleteDir(mCacheFile);
        tvCache.setText(FileUtil.getCacheSize(mCacheFile));
        showToast(getString(R.string.settingsActivity_clear_cache));
    }

    @Override
    public void upDataVersion() {
        PermissionHelper.getInstance().with(this).requestPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Constant.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE,
                new IPermissionCallback() {
                    @Override
                    public void onAccepted(Permission permission) {
                        DownloadUtil.downloadApk(SettingsActivity.this, mNewVersionName);

                    }

                    @Override
                    public void onDenied(Permission permission) {
                        showToast(getString(R.string.settingsActivity_permission_denied));
                    }

                    @Override
                    public void onDeniedAndReject(Permission permission) {
                        mGotoDetialDialog.get().show(getSupportFragmentManager(), GotoDetialDialog.class.getName());
                    }
                });
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
        view.setDrawingCacheEnabled(true);
        //view.buildDrawingCache(true);
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


    /**
     * 获取当前的语言设置
     */
    private String getLanguageHint(String lan) {
        String ret = "";
        switch (lan){
            case LanguageUtil.SYSTEM:
                ret = getString(R.string.dialog_lan_system);
                break;
            case LanguageUtil.SIMPLIFIED_CHINESE:
                ret = getString(R.string.dialog_lan_china);
                break;
            case LanguageUtil.ENGLISH:
                ret = getString(R.string.dialog_lan_english);
                break;
            default:
                ret = getString(R.string.dialog_lan_system);
                break;
        }
        return ret;
    }

}

