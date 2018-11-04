package com.example.hy.wanandroid.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.MainContract;
import com.example.hy.wanandroid.di.component.activity.DaggerMainActivityComponent;
import com.example.hy.wanandroid.di.component.activity.MainActivityComponent;
import com.example.hy.wanandroid.di.module.activity.MainActivityModule;
import com.example.hy.wanandroid.presenter.MainPresenter;
import com.example.hy.wanandroid.utils.LogUtil;
import com.example.hy.wanandroid.utils.ToastUtil;
import com.example.hy.wanandroid.view.hierarchy.HierarchyFragment;
import com.example.hy.wanandroid.view.homepager.HomeFragment;
import com.example.hy.wanandroid.view.mine.MineFragment;
import com.example.hy.wanandroid.view.project.ProjectFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import android.os.Handler;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.fbtn_up)
    FloatingActionButton fbtnUp;
    @BindView(R.id.bnv_btm)
    BottomNavigationView bnvBtm;

    private int mPreFragmentPosition = 0;//上一个被选中的Fragment位置
    private MainActivityComponent mMainActivityComponent;

    @Inject
    MainPresenter mPresenter;
    @Inject
    BaseFragment[] mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mMainActivityComponent = DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .build();
        mMainActivityComponent.inject(this);
        mPresenter.attachView(this);

        mFragments = new BaseFragment[4];
        if(savedInstanceState == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = HierarchyFragment.newInstance();
            mFragments[2] = ProjectFragment.newInstance();
            mFragments[3] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, 0, mFragments);
        }else {
            mFragments[0] = findFragment(HomeFragment.class);
            mFragments[1] = findFragment(HierarchyFragment.class);
            mFragments[2] = findFragment(ProjectFragment.class);
            mFragments[3] = findFragment(MineFragment.class);
        }
        bnvBtm.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.item_home:
                    showHideFragment(mFragments[0], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 0;
                    showFloatingButton();
                    break;
                case R.id.item_hierarchy:
                    showHideFragment(mFragments[1], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 1;
                    showFloatingButton();
                    break;
                case R.id.item_project:
                    showHideFragment(mFragments[2], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 2;
                    showFloatingButton();
                    break;
                case R.id.item_mine:
                    showHideFragment(mFragments[3], mFragments[mPreFragmentPosition]);
                    mPreFragmentPosition = 3;
                    hideFloatingButton();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressedSupport() {
        if(System.currentTimeMillis() - Constant.TOUCH_TIME < Constant.WAIT_TIME){
            finish();
        }else {
            Constant.TOUCH_TIME = System.currentTimeMillis();
            ToastUtil.showToast(this, getResources().getString(R.string.mainActivity_back));
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

    @Override
    protected void setStatusBarColor() {
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
    }

    public MainActivityComponent getComponent(){
        return mMainActivityComponent;
    }

    /**
     * 显示floatingButton
     */
    @SuppressLint("RestrictedApi")
    private void showFloatingButton(){
        if(fbtnUp.getVisibility() == View.INVISIBLE){
            fbtnUp.setVisibility(View.VISIBLE);
            fbtnUp.animate().setDuration(500).setInterpolator(new BounceInterpolator()).translationY(0).start();
        }
    }

    /**
     * 隐藏floatingButton
     */
    @SuppressLint("RestrictedApi")
    private void hideFloatingButton(){
        if(fbtnUp.getVisibility() == View.VISIBLE){
            ViewPropertyAnimator animator = fbtnUp.animate().setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 400, getResources().getDisplayMetrics())
            );
            new Handler().postDelayed(() -> fbtnUp.setVisibility(View.INVISIBLE), 301);
            animator.start();
        }
    }
}
