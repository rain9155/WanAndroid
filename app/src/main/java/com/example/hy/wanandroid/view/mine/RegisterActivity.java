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
import com.google.android.material.textfield.TextInputLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        tvLogin.setOnClickListener(v -> finish());
        ivBack.setOnClickListener(v -> finish());
        btnRegister.setOnClickListener(v -> attemptRegister());

    }

    @Override
    protected void initData() {

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        // Reset errors.
        tlAccount.setError(null);
        tlPassword.setError(null);
        tlPasswordAgain.setError(null);

        // Store values at the time of the login attempt.
        String account = atAccount.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passwordAgain = etPasswordAgain.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(account)) {
            tlAccount.setError(getString(R.string.registerActivity_error_account_empty));
            focusView = tlAccount;
            cancel = true;
        }else if(!isAccountValid(account)){
            tlAccount.setError(getString(R.string.registerActivity_error_account_invalid));
            focusView = tlAccount;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            tlPassword.setError(getString(R.string.loginActivity_error_password_empty));
            focusView = tlPassword;
            cancel = true;
        }else if(!isPasswordValid(password)){
            tlPassword.setError(getString(R.string.registerActivity_error_password_invalid));
            focusView = tlPassword;
            cancel = true;
        }else if(TextUtils.isEmpty(passwordAgain)){
            tlPasswordAgain.setError(getString(R.string.loginActivity_error_password_empty));
            focusView = tlPasswordAgain;
            cancel = true;
        }else if(!isPasswordValid(passwordAgain)){
            tlPasswordAgain.setError(getString(R.string.registerActivity_error_password_invalid));
            focusView = tlPasswordAgain;
            cancel = true;
        }else if(password.compareTo(passwordAgain) != 0){
            tlPassword.setError(getString(R.string.registerActivity_error_passwordAgain_invalid));
            tlPasswordAgain.setError(getString(R.string.registerActivity_error_passwordAgain_invalid));
            focusView = tlPassword;
            cancel = true;
        }

        // There was an error; don't attempt login and focus the first
        // form field with an error.
        if (cancel) {
            focusView.requestFocus();
            return;
        }

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(true);
        //login
    }

    private boolean isAccountValid(String account) {
        return account.length() > 6;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        clRegister.setVisibility(show ? View.GONE : View.VISIBLE);
        clRegister.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                clRegister.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        loginProgress.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

}
