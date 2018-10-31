package com.example.hy.wanandroid.view.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.contract.navigation.NavigationContract;
import com.example.hy.wanandroid.di.component.activity.DaggerNavigationActivityComponent;
import com.example.hy.wanandroid.network.entity.navigation.Tag;
import com.example.hy.wanandroid.presenter.navigation.NavigationPresenter;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.verticaltablayout.VerticalTabLayout;

public class NavigationActivity extends BaseActivity implements NavigationContract.View {

    @BindView(R.id.vtl_navigation)
    VerticalTabLayout vtlNavigation;
    @BindView(R.id.rv_navigation)
    RecyclerView rvNavigation;


    @Inject
    NavigationPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DaggerNavigationActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, NavigationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showTags(List<Tag> tagList) {

    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
