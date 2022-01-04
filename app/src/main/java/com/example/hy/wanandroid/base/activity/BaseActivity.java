package com.example.hy.wanandroid.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.component.NetWorkChangeReceiver;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.utlis.StatusBarUtil;
import com.example.hy.wanandroid.utlis.ToastUtil;
import com.example.hy.wanandroid.event.ReLoadEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity的基类
 * Created by 陈健宇 at 2018/10/21
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {

    private Unbinder mUnbinder;
    private NetWorkChangeReceiver mNetWorkChangeReceiver;
    private TextView mTipView;
    protected boolean isEnableTip = true;
    protected void inject(){}//根据需要注入
    protected abstract int getLayoutId();//获取Activity的布局Id
    protected abstract void initView();//初始化控件
    protected abstract void initData();//初始化数据

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        inject();
        initView();
        setStatusBarColor(getAppComponent().getDataModel().getStatusBarState());
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
        if (!isEnableTip)
            return;
        if(mTipView == null)
            mTipView = new TextView(this);

        if (isConnection){
            if(mTipView.getParent() != null)
                ((ViewGroup)getWindow().getDecorView()).removeView(mTipView);
            RxBus.getInstance().post(new ReLoadEvent());
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
    public void showToast(String toast) {
        ToastUtil.showCustomToastInBottom(this, toast);
    }

    @Override
    public void unableRefresh() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showToast(Activity activity, String toast) {

    }

    @Override
    public void showErrorMes() {

    }
}
