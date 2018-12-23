package com.example.hy.wanandroid.base.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.component.NetWorkChangeReceiver;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.commonlib.utils.StatusBarUtil;
import com.example.commonlib.utils.ToastUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Activity的基类
 * Created by 陈健宇 at 2018/10/21
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private Unbinder mUnbinder;
    private NetWorkChangeReceiver mNetWorkChangeReceiver;
    private TextView mTipView;
    protected boolean isEnableTip = true;
    protected abstract int getLayoutId();//获取Activity的布局Id
    protected abstract void initView();//初始化控件
    protected abstract void initData();//初始化数据

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        setStatusBarColor(getAppComponent().getDataModel().getStatusBarState());
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mNetWorkChangeReceiver = new NetWorkChangeReceiver();
        registerReceiver(mNetWorkChangeReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mNetWorkChangeReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(mUnbinder != null && mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
        if(mTipView != null && mTipView.getParent() != null) ((ViewGroup)getWindow().getDecorView()).removeView(mTipView);
        super.onDestroy();
    }

    @Override
    public void setStatusBarColor(boolean isSet) {
        if(isSet){
            StatusBarUtil.immersive(this, getResources().getColor(R.color.colorPrimary));
        }else {
            StatusBarUtil.immersive(this, getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void showTipsView(boolean isConnection) {
        if (!isEnableTip) return;
        if(mTipView == null) mTipView = new TextView(this);
        if (isConnection){
            if(mTipView.getParent() != null) ((ViewGroup)getWindow().getDecorView()).removeView(mTipView);
            reLoad();
        }
        else{
            if(mTipView.getParent() != null) return;
            ToastUtil.toastMake(
                    mTipView,
                    (ViewGroup) getWindow().getDecorView(),
                    getString(R.string.error_unavailable),
                    ContextCompat.getColor(this, R.color.colorTipBackground),
                    ContextCompat.getColor(this, R.color.colorTip));
        }
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
    public void useNightNode(boolean isNight) {
        if(isNight){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    public void showToast(String toast) {
        ToastUtil.toastInBottom(App.getContext(), toast);
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
