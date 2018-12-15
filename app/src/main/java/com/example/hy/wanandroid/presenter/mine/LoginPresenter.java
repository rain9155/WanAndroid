package com.example.hy.wanandroid.presenter.mine;

import android.text.TextUtils;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.mine.LoginContract;
import com.example.hy.wanandroid.event.LoginEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Login;
import com.example.hy.wanandroid.utils.RxUtils;

import javax.inject.Inject;

/**
 * Created by 陈健宇 at 2018/11/16
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void login(String account, String password) {

        // Check for a valid email address.
        boolean cancel = false;
        if (TextUtils.isEmpty(account)) {
            mView.setAccountErrorView(App.getContext().getResources().getString(R.string.loginActivity_error_account_empty));
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mView.setPasswordErrorView(App.getContext().getResources().getString(R.string.loginActivity_error_password_empty));
            cancel = true;
        }
        // There was an error; don't attempt login and focus the first
        // form field with an error.
        if(cancel){
            mView.requestFocus(cancel);
            return;
        }

        addSubcriber(
                mModel.getLoginRequest(account, password)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<Login>(mView) {
                    @Override
                    public void onNext(Login login) {
                        super.onNext(login);
                        User user = User.getInstance();
                        user.setLoginStatus(true);
                        user.setPassword(password);
                        user.setUsername(account);
                        user.save();
                        RxBus.getInstance().post(new LoginEvent(true));
                        mView.loginSuccess();
                    }
                })
        );

    }

}
