package com.example.hy.wanandroid.view.mine;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.contract.mine.RegisterContract;
import com.example.hy.wanandroid.di.component.activity.DaggerRegisterActivityComponent;
import com.example.hy.wanandroid.presenter.mine.RegisterPresenter;
import com.example.hy.wanandroid.widget.dialog.LoadingDialog;
import com.example.hy.wanandroid.widget.dialog.LogoutDialog;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    @BindView(R.id.iv_face)
    CircleImageView ivFace;
    @BindView(R.id.at_account)
    AutoCompleteTextView atAccount;
    @BindView(R.id.tl_account)
    TextInputLayout tlAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tl_password)
    TextInputLayout tlPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.tl_password_again)
    TextInputLayout tlPasswordAgain;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.cl_register)
    ConstraintLayout clRegister;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Inject
    RegisterPresenter mPresenter;
    @Inject
    LoadingDialog mLoadingDialog;

    private View focusView = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView( ) {

        DaggerRegisterActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
        mPresenter.attachView(this);

        tvLogin.setOnClickListener(v -> finish());
        ivBack.setOnClickListener(v -> finish());
        btnRegister.setOnClickListener(v -> {
            tlAccount.setError(null);
            tlPasswordAgain.setError(null);
            tlPasswordAgain.setError(null);
            mPresenter.register(
                    atAccount.getText().toString().trim(),
                    etPassword.getText().toString().trim(),
                    etPasswordAgain.getText().toString().trim()
            );
        });

    }

    @Override
    protected void initData() {

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
    public void setAccountErrorView(String error) {
        focusView = tlAccount;
        tlAccount.setError(error);
    }

    @Override
    public void setPasswordErrorView(String error) {
        focusView = tlPassword;
        tlPassword.setError(error);
    }

    @Override
    public void setRePasswordErrorView(String error) {
        focusView = tlPasswordAgain;
        tlPasswordAgain.setError(error);
    }

    @Override
    public void requestFocus(boolean cancel) {
        if(!cancel || focusView == null) return;
        focusView.requestFocus();
    }

    @Override
    public void registerSuccess() {
        showToast(getString(R.string.registerActivity_register_success));
        finish();
    }

    @Override
    public void showErrorView() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showNormalView() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show(getSupportFragmentManager(), "tag3");
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}
