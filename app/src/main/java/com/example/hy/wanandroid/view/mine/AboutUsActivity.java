package com.example.hy.wanandroid.view.mine;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.commonlib.utils.StatusBarUtil;
import com.example.hy.wanandroid.widget.interpolator.ElasticOutInterpolator;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.flyrefresh.FlyView;
import com.scwang.smartrefresh.header.flyrefresh.MountainSceneView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.mountain)
    MountainSceneView mountain;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.flyView)
    FlyView flyView;
    @BindView(R.id.flyRefreshHeader)
    FlyRefreshHeader flyRefreshHeader;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setHeightAndPadding(this, toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());

        isEnableTip = false;

        flyRefreshHeader.setUp(mountain, flyView);//绑定场景和纸飞机
        refreshLayout.setReboundInterpolator(new ElasticOutInterpolator());//设置回弹插值器，会带有弹簧震动效果
        refreshLayout.setReboundDuration(1000);//设置回弹动画时长
        refreshLayout.setOnRefreshListener(refreshLayout1 -> refreshLayout1.finishRefresh(1000));//下拉刷新监听
        //设置让Toolbar和AppBarLayout的滚动同步
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
                if(appBar == null || toolbar == null) return;
                appBar.setTranslationY(offset);
                toolbar.setTranslationY(-offset);
            }
        });
        //监听 AppBarLayout 的关闭和开启 给 FlyView（纸飞机） 和 fab 设置关闭隐藏动画
        //verticalOffset向上滑动为负值。向下滑动为正值
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean misAppbarExpand = true;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
                double minFraction = 0.1;
                double maxFraction = 0.8;
                if(fab == null || flyView == null || scrollView == null) return;
                if (fraction < minFraction && misAppbarExpand) {
                    misAppbarExpand = false;
                    fab.animate().scaleX(0).scaleY(0);
                    flyView.animate().scaleX(0).scaleY(0);
                    ValueAnimator animator = ValueAnimator.ofInt(scrollView.getPaddingTop(), 0);
                    animator.setDuration(300);
                    animator.addUpdateListener(animation -> {
                        if (scrollView != null) {
                            scrollView.setPadding(0, (int) animation.getAnimatedValue(), 0, 0);
                        }
                    });
                    animator.start();
                }
                if (fraction > maxFraction && !misAppbarExpand) {
                    misAppbarExpand = true;
                    fab.animate().scaleX(1).scaleY(1);
                    flyView.animate().scaleX(1).scaleY(1);
                    ValueAnimator animator = ValueAnimator.ofInt(scrollView.getPaddingTop(), DensityUtil.dp2px(25));
                    animator.setDuration(300);
                    animator.addUpdateListener(animation -> {
                        if (scrollView != null) {
                            scrollView.setPadding(0, (int) animation.getAnimatedValue(), 0, 0);
                        }
                    });
                    animator.start();
                }
            }
        });
        //refreshLayout.autoRefresh();//进入界面时自动刷新
        fab.setOnClickListener(v -> refreshLayout.autoRefresh());//点击悬浮按钮时自动刷新
        tvContent.setText(Html.fromHtml(getString(R.string.aboutActivity_about_us_text)));
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }
}
