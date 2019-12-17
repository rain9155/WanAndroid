package com.example.hy.wanandroid.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import com.example.commonlib.utils.FileProvider7;
import com.example.commonlib.utils.LogUtil;
import com.example.commonlib.utils.TimeUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseMvpActivity;
import com.example.permission.bean.Permission;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.MainContract;
import com.example.hy.wanandroid.di.component.activity.DaggerMainActivityComponent;
import com.example.hy.wanandroid.di.component.activity.MainActivityComponent;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.presenter.MainPresenter;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.utlis.DownloadUtil;
import com.example.commonlib.utils.StatusBarUtil;
import com.example.commonlib.utils.ToastUtil;
import com.example.hy.wanandroid.view.hierarchy.HierarchyFragment;
import com.example.hy.wanandroid.view.homepager.HomeFragment;
import com.example.hy.wanandroid.view.mine.MineFragment;
import com.example.hy.wanandroid.view.project.ProjectFragment;
import com.example.hy.wanandroid.view.wechat.WeChatFragment;
import com.example.hy.wanandroid.widget.dialog.GotoDetialDialog;
import com.example.hy.wanandroid.widget.dialog.OpenBrowseDialog;
import com.example.hy.wanandroid.widget.dialog.VersionDialog;
import com.example.permission.PermissionHelper;
import com.example.permission.bean.SpecialPermission;
import com.example.permission.callback.IPermissionCallback;
import com.example.permission.callback.ISpecialPermissionCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import butterknife.BindView;
import dagger.Lazy;


import java.io.File;
import java.util.HashMap;

import javax.inject.Inject;


public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.fbtn_up)
    FloatingActionButton fbtnUp;
    @BindView(R.id.bnv_btm)
    BottomNavigationView bnvBtm;

    private int mPreFragmentPosition = 0;//上一个被选中的Fragment位置
    private MainActivityComponent mMainActivityComponent;
    private ObjectAnimator  mShowNavAnimator;
    private ViewPropertyAnimator mHideFbtnAnimator, mShowFbtnAnimator;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenter mPresenter;
    @Inject
    Fragment[] mFragments;
    @Inject
    Lazy<VersionDialog> mVersionDialog;
    @Inject
    OpenBrowseDialog mOpenBrowseDialog;
    @Inject
    Lazy<GotoDetialDialog> mGotoDetialDialog;

    private String mNewVersionName;
    private boolean isSetStatusBar;
    private int mStatusBarColor = R.color.colorPrimary;
    private boolean hasSetStatusBar;

    @Override
    protected void inject() {
        mMainActivityComponent = DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .build();
        mMainActivityComponent.inject(this);
    }


    @Override
    protected MainPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColors(isSetStatusBar);
        if(savedInstanceState == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = HierarchyFragment.newInstance();
            mFragments[2] = WeChatFragment.newInstance();
            mFragments[3] = ProjectFragment.newInstance();
            mFragments[4] = MineFragment.newInstance();
            loadMultipleFragment(R.id.fl_container, 0, mFragments);
        }else {
            mFragments[0] = findFragmentByTag(HomeFragment.class.getName());
            mFragments[1] = findFragmentByTag(HierarchyFragment.class.getName());
            mFragments[2] = findFragmentByTag(WeChatFragment.class.getName());
            mFragments[3] = findFragmentByTag(ProjectFragment.class.getName());
            mFragments[4] = findFragmentByTag(MineFragment.class.getName());
            bnvBtm.setSelectedItemId(getSelectedId(mPresenter.getCurrentItem()));
        }
    }

    @Override
    protected void initView() {
        super.initView();
        bnvBtm.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.item_home:
                    showAndHideFragment(mFragments[0], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 0;
                    showFloatingButton();
                    setStatusBarColors(isSetStatusBar);
                    break;
                case R.id.item_hierarchy:
                    showAndHideFragment(mFragments[1], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 1;
                    showFloatingButton();
                    setStatusBarColors(isSetStatusBar);
                    break;
                case R.id.item_wechat:
                    showAndHideFragment(mFragments[2], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 2;
                    showFloatingButton();
                    setStatusBarColors(isSetStatusBar);
                    break;
                case R.id.item_project:
                    showAndHideFragment(mFragments[3], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 3;
                    showFloatingButton();
                    setStatusBarColors(isSetStatusBar);
                    break;
                case R.id.item_mine:
                    //顶部带照片的Fragment特殊处理
                    showAndHideFragment(mFragments[4], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 4;
                    hideFloatingButton();
                    StatusBarUtil.immersiveInImage(this);
                    break;
                default:
                    break;
            }
            return true;
        });

        fbtnUp.setOnClickListener(v -> {
            RxBus.getInstance().post(new ToppingEvent());
            show(bnvBtm);
        });

        if(mPresenter.getAutoUpdataState()) mPresenter.checkVersion(DownloadUtil.getVersionName(this));

    }

    @Override
    public void onBackPressed() {
        if(TimeUtil.isInInterval(Constant.EXIT_WAIT_TIME)){
            finish();
        }else {
            ToastUtil.toastInCenter(this, getString(R.string.mainActivity_back));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.setCurrentItem(mPreFragmentPosition);
    }

    @Override
    protected void onStop() {
        if(mHideFbtnAnimator != null) mHideFbtnAnimator.cancel();
        if(mShowFbtnAnimator != null) mShowFbtnAnimator.cancel();
        if(mShowNavAnimator != null) mShowNavAnimator.cancel();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(mOpenBrowseDialog != null)
            mOpenBrowseDialog = null;
        if(mVersionDialog.get() != null)
            mVersionDialog = null;
        if(mGotoDetialDialog.get() != null)
            mGotoDetialDialog = null;
        if(mShowFbtnAnimator != null)
            mShowFbtnAnimator.cancel();
        if(mHideFbtnAnimator != null)
            mHideFbtnAnimator.cancel();
        super.onDestroy();
    }

    @Override
    public void showUpdataDialog(String content) {
        mVersionDialog.get().setContentText(content);
        mVersionDialog.get().setIsMain(true);
        mVersionDialog.get().show(getSupportFragmentManager(), VersionDialog.class.getName());
    }

    @Override
    public void setNewVersionName(String versionName) {
        mNewVersionName = versionName;
    }

    @Override
    public void upDataVersion() {
        PermissionHelper.getInstance().with(this).requestPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                new IPermissionCallback() {
                    @Override
                    public void onAccepted(Permission permission) {
                        DownloadUtil.downloadApk(MainActivity.this, mNewVersionName);
                    }

                    @Override
                    public void onDenied(Permission permission) {
                        showToast(getString(R.string.settingsActivity_permission_denied));
                    }

                    @Override
                    public void onDeniedAndReject(Permission permission) {
                        mGotoDetialDialog.get().show(getSupportFragmentManager(), GotoDetialDialog.class.getSimpleName());

                    }
                });
    }

    @Override
    public void showOpenBrowseDialog() {
        mOpenBrowseDialog.show(getSupportFragmentManager(), OpenBrowseDialog.class.getName());
    }

    @Override
    public void installApk() {
        PermissionHelper.getInstance().with(this).requestSpecialPermission(
                SpecialPermission.INSTALL_UNKNOWN_APP,
                new ISpecialPermissionCallback() {
                    @Override
                    public void onAccepted(SpecialPermission permission) {
                        LogUtil.d(TAG, "onAccepted(): " + permission.toString());
                        installApk(MainActivity.this);
                    }

                    @Override
                    public void onDenied(SpecialPermission permission) {
                        LogUtil.d(TAG, "onDenied(): " + permission.toString());
                    }
                }
        );
    }

    public MainActivityComponent getComponent(){
        return mMainActivityComponent;
    }

    @Override
    public void setStatusBarColor(boolean isSet) {
        this.isSetStatusBar = isSet;
        this.hasSetStatusBar = false;
        if(isSet){
            mStatusBarColor = R.color.colorPrimary;
        }else {
            mStatusBarColor = R.color.colorPrimaryDark;
        }
    }

    /**
     * 装载Fragments
     */
    private void loadMultipleFragment(int containerId, int showFragment, Fragment... fragments){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for(int i = 0; i < fragments.length; i++){
            Fragment fragment = fragments[i];
            transaction.add(containerId, fragment, fragment.getClass().getName());
            if(i != showFragment){
                transaction.hide(fragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 根据tag找到fragment实例
     */
    private Fragment findFragmentByTag(String tag){
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    /**
     * 显示和隐藏fragment
     */
    private void showAndHideFragment(Fragment show, Fragment hide){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(show != hide)
            transaction.show(show).hide(hide).commitAllowingStateLoss();
    }

    /**
     * 获得被选中的item
     */
    private int getSelectedId(int currentItem) {
        int id;
        switch (currentItem){
            case 0:
                id = R.id.item_home;
                break;
            case 1:
                id = R.id.item_hierarchy;
                break;
            case 2:
                id = R.id.item_wechat;
                break;
            case 3:
                id = R.id.item_project;
                break;
            case 4:
                id = R.id.item_mine;
                break;
            default:
                id = R.id.item_home;
                break;
        }
        return id;
    }


    /**
     * 显示floatingButton
     */
    @SuppressLint("RestrictedApi")
    private void showFloatingButton(){
        if(fbtnUp.getVisibility() == View.INVISIBLE){
            fbtnUp.setVisibility(View.VISIBLE);
            mShowFbtnAnimator = fbtnUp.animate().setDuration(500).setInterpolator(new BounceInterpolator()).translationY(0);
            mShowFbtnAnimator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    fbtnUp.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    /**
     * 隐藏floatingButton
     */
    @SuppressLint("RestrictedApi")
    private void hideFloatingButton(){
        if(fbtnUp.getVisibility() == View.VISIBLE){
            mHideFbtnAnimator = fbtnUp.animate().setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 400, getResources().getDisplayMetrics())
            );
            mHideFbtnAnimator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    fbtnUp.setVisibility(View.INVISIBLE);
                }
            });
            mHideFbtnAnimator.start();
        }
    }

    /**
     * 底部导航栏显示
     */
    private void show(View child) {
        if(mShowNavAnimator == null){
            mShowNavAnimator = ObjectAnimator.ofFloat(child, "translationY", child.getHeight(), 0)
                    .setDuration(200);
            mShowNavAnimator.setInterpolator(new FastOutSlowInInterpolator());
        }
        if(!mShowNavAnimator.isRunning() && child.getTranslationY() >= child.getHeight()){
            mShowNavAnimator.start();
        }
    }

    /**
     * 安装应用
     */
    private void installApk(Context context) {
        LogUtil.d(LogUtil.TAG_COMMON, "安装应用");
        File file = new File(Constant.PATH_APK);
        if (file.exists()) {
            Intent install = new Intent("android.intent.doWork.VIEW");
            FileProvider7.setIntentDataAndType(
                    App.getContext(),
                    install,
                    "application/vnd.android.package-archive",
                    file,
                    false
            );
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);
        }else {
            LogUtil.d(LogUtil.TAG_COMMON, "应用路径不存在");
            ToastUtil.toastInBottom(context, context.getString(R.string.setup_fail));
        }
    }

    /**
     * MainActivity中含有普通Fragment和顶部带照片的Fragment，所以特殊处理
     */
    private void setStatusBarColors(boolean isSet){
        if(hasSetStatusBar) return;
        hasSetStatusBar = true;
        if(isSet){
            StatusBarUtil.immersiveInFragments(this, getResources().getColor(mStatusBarColor), 1);
        }else {
            StatusBarUtil.immersiveInFragments(this, getResources().getColor(mStatusBarColor), 1);
        }
    }

}
