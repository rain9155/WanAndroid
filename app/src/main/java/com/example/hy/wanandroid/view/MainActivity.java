package com.example.hy.wanandroid.view;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.contract.MainContract;
import com.example.hy.wanandroid.view.hierarchy.HierarchyFragment;
import com.example.hy.wanandroid.view.homepager.HomeFragment;
import com.example.hy.wanandroid.view.mine.MineFragment;
import com.example.hy.wanandroid.view.project.ProjectFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import android.os.Handler;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.fbtn_up)
    FloatingActionButton fbtnUp;
    @BindView(R.id.bnv_btm)
    BottomNavigationView bnvBtm;
    private SwipeBackFragment[] mFragments;
    private int mPreFragmentPosition = 0;//上一个被选中的Fragment位置

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        if(findFragment(HomeFragment.class) == null){
            mFragments = new SwipeBackFragment[4];
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = HierarchyFragment.newInstance();
            mFragments[2] = ProjectFragment.newInstance();
            mFragments[3] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, 0, mFragments[0], mFragments[1], mFragments[2], mFragments[3]);
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
    protected void setStatusBarColor() {
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
    }

    /**
     * 显示floatingButton
     */
    @SuppressLint("RestrictedApi")
    private void showFloatingButton(){
        if(fbtnUp.getVisibility() == View.INVISIBLE){
            fbtnUp.setVisibility(View.VISIBLE);
            fbtnUp.animate().setDuration(500).setInterpolator(new BounceInterpolator()).translationY(
                    -TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 0, getResources().getDisplayMetrics())
            ).start();
        }
    }

    /**
     * 隐藏floatingButton
     */
    @SuppressLint("RestrictedApi")
    private void hideFloatingButton(){
        if(fbtnUp.getVisibility() == View.VISIBLE){
            fbtnUp.animate().setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 400, getResources().getDisplayMetrics())
            ).start();
            new Handler().postDelayed(() -> fbtnUp.setVisibility(View.INVISIBLE),301);
        }

    }

}
