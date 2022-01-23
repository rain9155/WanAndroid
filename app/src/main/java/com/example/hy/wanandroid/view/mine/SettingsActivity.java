package com.example.hy.wanandroid.view.mine;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hy.wanandroid.entity.Apk;
import com.example.hy.wanandroid.utlis.CommonUtil;
import com.example.hy.wanandroid.utlis.LanguageUtil;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.hy.wanandroid.utlis.FileUtil;
import com.example.hy.wanandroid.utlis.ServiceUtil;
import com.example.hy.wanandroid.utlis.ShareUtil;
import com.example.hy.wanandroid.utlis.StatusBarUtil;
import com.example.hy.wanandroid.utlis.ThemeUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseMvpActivity;
import com.example.hy.wanandroid.widget.dialog.GotoDetailDialog;
import com.example.hy.wanandroid.widget.dialog.ThemeDialog;
import com.example.hy.wanandroid.component.UpdateService;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.mine.SettingsContract;
import com.example.hy.wanandroid.presenter.mine.SettingsPresenter;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.widget.dialog.ClearCacheDialog;
import com.example.hy.wanandroid.widget.dialog.LanguageDialog;
import com.example.hy.wanandroid.widget.dialog.VersionDialog;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Provider;

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
    @BindView(R.id.iv_theme)
    ImageView ivTheme;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_theme_hint)
    TextView tvThemeHint;
    @BindView(R.id.cl_theme)
    ConstraintLayout clTheme;

    @Inject
    SettingsPresenter mPresenter;
    @Inject
    File mCacheFile;
    @Inject
    Lazy<VersionDialog> mVersionDialog;
    @Inject
    Lazy<ClearCacheDialog> mClearCacheDialog;
    @Inject
    Lazy<GotoDetailDialog> mGotoDetialDialog;
    @Inject
    Lazy<LanguageDialog> mLanguageDialog;
    @Inject
    Provider<ThemeDialog> mThemeDialogProvider;

    private ObjectAnimator mAnimator;

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected SettingsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        super.initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setHeightAndPadding(this, tlCommon);
        }
        isEnableTip = false;
        initToolBar();
        initSwitch();
        initSettings();
    }

    private void initSettings() {
        String currentVersionName = CommonUtil.getVersionName(this);
        tvVersion.setText(getString(R.string.settingsActivity_version_current) + currentVersionName);

        clClearCache.setOnClickListener(v -> {
            String cache = FileUtil.getCacheSize(mCacheFile);
            if ("0K".equals(cache)) {
                showToast(getString(R.string.settingsActivity_already_clear));
            } else {
                mClearCacheDialog.get().showWithCache(getSupportFragmentManager(), ClearCacheDialog.class.getName(), cache);
            }
        });
        tvCache.setText(FileUtil.getCacheSize(mCacheFile));

        clLanguage.setOnClickListener(v -> mLanguageDialog.get().show(getSupportFragmentManager(), LanguageDialog.class.getName()));
        tvLanguageHint.setText(getLanguageHint(mPresenter.getSelectedLanguage()));

        clTheme.setOnClickListener(v -> mThemeDialogProvider.get().show(getSupportFragmentManager(), ThemeDialog.class.getName()));
        tvThemeHint.setText(getThemeHint(mPresenter.getSelectedTheme()));

        clFeedBack.setOnClickListener(v -> ShareUtil.sendEmail(this, Constant.EMAIL_ADDRESS, getString(R.string.settingsActivity_email_to)));

        clMes.setOnClickListener(v -> ShareUtil.gotoAppDetailIntent(this));

        clUpdata.setOnClickListener(v -> {
            if (ServiceUtil.isServiceRunning(this, UpdateService.class.getName())) {
                showToast(getString(R.string.downloading));
                return;
            }
            if (mAnimator != null && mAnimator.isRunning()) {
                return;
            }
            mPresenter.checkVersion(currentVersionName);
        });

    }


    private void initSwitch() {
        switchNoImage.setChecked(mPresenter.getNoImageState());
        switchAutoCache.setChecked(mPresenter.getAutoCacheState());
        switchStatusBar.setChecked(mPresenter.getStatusBarState());
        switchAutoUpdata.setChecked(mPresenter.getAutoUpdateState());
        switchAutoCache.setOnCheckedChangeListener(this);
        switchNoImage.setOnCheckedChangeListener(this);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_autoCache:
                mPresenter.setAutoCacheState(isChecked);
                break;
            case R.id.switch_noImage:
                mPresenter.setNoImageState(isChecked);
                break;
            case R.id.switch_status_bar:
                mPresenter.setStatusBarState(isChecked);
                break;
            case R.id.switch_auto_updata:
                mPresenter.setAutoUpdateState(isChecked);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        super.onStop();
    }

    @Override
    public void showLoading() {
        startLoadAnimator();
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
    public void showUpdateDialog(Apk apk) {
        mAnimator.cancel();
        if(apk == null) {
            return;
        }
        if(apk.isNeedUpdate()) {
            mVersionDialog.get().showWithApkInfo(getSupportFragmentManager(), VersionDialog.class.getName(), apk);
        }else {
            showToast(getString(R.string.dialog_version_already));
        }
    }

    @Override
    public void handleLanguageChange() {
        LanguageUtil.attachBaseContext(this, mPresenter.getSelectedLanguage());
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void handleThemeChange() {
        startActivity(new Intent(this, SettingsActivity.class));
        overridePendingTransition(R.anim.anim_settings_enter, R.anim.anim_settings_exit);
        finish();
        String theme = mPresenter.getSelectedTheme();
        ThemeUtil.setTheme(this, theme);
    }

    @Override
    public void clearCache() {
        FileUtil.deleteDir(mCacheFile);
        tvCache.setText(FileUtil.getCacheSize(mCacheFile));
        showToast(getString(R.string.settingsActivity_clear_cache));
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 启动load动画
     */
    @SuppressLint("WrongConstant")
    private void startLoadAnimator() {
        mAnimator = ObjectAnimator.ofFloat(ivUpdata, "rotation", 0, 360).setDuration(600);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    /**
     * 添加一个渐变的Bitmap在DecorView上
     */
    @Deprecated
    private void addBitmapOnDecorView() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackground(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
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

    /**
     * 获取一个View的缓存视图
     */
    @Deprecated
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
        String ret;
        switch (lan){
            case LanguageUtil.SIMPLIFIED_CHINESE:
                ret = getString(R.string.dialog_china);
                break;
            case LanguageUtil.ENGLISH:
                ret = getString(R.string.dialog_english);
                break;
            default:
                ret = getString(R.string.dialog_system);
                break;
        }
        return ret;
    }

    /**
     * 获取当前的主题设置
     */
    private String getThemeHint(String theme) {
        String ret;
        switch (theme){
            case ThemeUtil.DARK:
                ret = getString(R.string.dialog_dark);
                break;
            case ThemeUtil.LIGHT:
                ret = getString(R.string.dialog_light);
                break;
            default:
                ret = getString(R.string.dialog_system);
                break;
        }
        return ret;
    }

}

