package com.example.hy.wanandroid.view.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseMvpActivity;
import com.example.hy.wanandroid.contract.mine.LoginContract;
import com.example.hy.wanandroid.presenter.mine.LoginPresenter;
import com.example.hy.wanandroid.utlis.KeyBoardUtil;
import com.example.hy.wanandroid.utlis.StatusBarUtil;
import com.example.hy.wanandroid.widget.customView.ShapeImageView;
import com.example.hy.wanandroid.widget.dialog.LoadingDialog;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import dagger.Lazy;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View{

    @BindView(R.id.ib_back)
    ImageView ibBack;
    @BindView(R.id.iv_face)
    ShapeImageView ivFace;
    @BindView(R.id.at_account)
    AutoCompleteTextView atAccount;
    @BindView(R.id.tl_account)
    TextInputLayout tlAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tl_password)
    TextInputLayout tlPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.cl_login)
    ConstraintLayout clLogin;
    @BindView(R.id.root_view)
    ConstraintLayout rootView;

    @Inject
    LoginPresenter mPresenter;
    @Inject
    Lazy<LoadingDialog> mLoadingDialog;

    private View focusView = null;
    private static boolean isNeedResult = false;

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initView() {
        super.initView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setPaddingSmart(this, rootView);
        ibBack.setOnClickListener(v -> finish());
        tvRegister.setOnClickListener(v -> RegisterActivity.startActivity(this));
        btnLogin.setOnClickListener(v -> {
            // Reset errors.
            tlAccount.setError(null);
            tlPassword.setError(null);
            // Store values at the time of the login attempt.
            KeyBoardUtil.closeKeyBoard(this, atAccount);
            KeyBoardUtil.closeKeyBoard(this, etPassword);
            mPresenter.login(atAccount.getText().toString().trim(), etPassword.getText().toString().trim());
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoadingDialog != null)
            mLoadingDialog = null;
    }

    @Override
    public void showLoading() {
        mLoadingDialog.get().show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void showNormalView() {
        mLoadingDialog.get().dismiss();
    }

    @Override
    public void showErrorView() {
        mLoadingDialog.get().dismiss();
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
    public void requestFocus(boolean cancel) {
        if (focusView == null || !cancel) return;
        focusView.requestFocus();
    }

    @Override
    public void loginSuccess() {
        showToast(getString(R.string.loginActivity_success));
        if (isNeedResult) setResult(RESULT_OK);
        finish();
    }

    public static void startActivity(Context context) {
        isNeedResult = false;
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, int request) {
        isNeedResult = true;
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, request);
    }

    public static void startActivityForResultByFragment(Activity activity, Fragment fragment, int request) {
        isNeedResult = true;
        Intent intent = new Intent(activity, LoginActivity.class);
        fragment.startActivityForResult(intent, request);
    }

}

