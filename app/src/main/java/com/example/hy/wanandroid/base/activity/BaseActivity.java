package com.example.hy.wanandroid.base.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.event.LoginEvent;
import com.example.hy.wanandroid.utils.SnackUtil;
import com.example.hy.wanandroid.utils.ToastUtil;
import com.example.hy.wanandroid.view.mine.LoginActivity;
import com.jaeger.library.StatusBarUtil;

import androidx.appcompat.app.AppCompatDelegate;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Activity的基类
 * Created by 陈健宇 at 2018/10/21
 */
public abstract class BaseActivity extends SupportActivity
        implements BaseView {

    private Unbinder mUnbinder;

    protected abstract int getLayoutId();//获取Activity的布局Id
    protected abstract void initView();//初始化控件
    protected abstract void initData();//初始化数据

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        setStatusBarColor();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        if(mUnbinder != null && mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBarColor(){
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    protected AppComponent getAppComponent(){
        return ((App)getApplication()).getAppComponent();
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showErrorMes() {

    }

    @Override
    public void reLoad() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void userNightNode(boolean isNight) {
        if(isNight){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    public void tokenExpire(int requestCode) {
        User.getInstance().reset();
        RxBus.getInstance().post(new LoginEvent(false));
        LoginActivity.startActivityForResult(this, requestCode);
    }

    @Override
    public void showToast(String toast) {
        ToastUtil.toastInBottom(this, toast, null);
    }

    @Override
    public void showSnackBar(String toast) {
        SnackUtil.showSnackBar(this, toast);
    }

    @Override
    public void showNormalView() {

    }

    @Override
    public void unableRefresh() {

    }

    @Override
    public void showDialog() {

    }
}
