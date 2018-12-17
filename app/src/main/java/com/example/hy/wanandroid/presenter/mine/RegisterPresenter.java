package com.example.hy.wanandroid.presenter.mine;

import android.text.TextUtils;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.contract.mine.RegisterContract;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Login;
import com.example.hy.wanandroid.utlis.RxUtils;

import javax.inject.Inject;

/**
 * Register的Presenter
 * Created by 陈健宇 at 2018/11/19
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Inject
    public RegisterPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void register(String username, String password, String rePassword) {
        boolean cancel = false;

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mView.setAccountErrorView(App.getContext().getString(R.string.registerActivity_error_account_empty));
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mView.setPasswordErrorView(App.getContext().getString(R.string.loginActivity_error_password_empty));
            cancel = true;
        }else if(!isPasswordValid(password)){
            mView.setPasswordErrorView(App.getContext().getString(R.string.registerActivity_error_password_invalid));
            cancel = true;
        }else if(TextUtils.isEmpty(rePassword)){
            mView.setRePasswordErrorView(App.getContext().getString(R.string.loginActivity_error_password_empty));
            cancel = true;
        }else if(!isPasswordValid(rePassword)){
            mView.setRePasswordErrorView(App.getContext().getString(R.string.registerActivity_error_password_invalid));
            cancel = true;
        }else if(password.compareTo(rePassword) != 0){
            mView.setPasswordErrorView(App.getContext().getString(R.string.registerActivity_error_passwordAgain_invalid));
            mView.setRePasswordErrorView(App.getContext().getString(R.string.registerActivity_error_passwordAgain_invalid));
            cancel = true;
        }

        // There was an error; don't attempt login and focus the first
        // form field with an error.
        if (cancel) {
            mView.requestFocus(cancel);
            return;
        }

        addSubcriber(
                mModel.getRegisterRequest(username, password, rePassword)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<Login>(mView){
                    @Override
                    public void onNext(Login login) {
                        super.onNext(login);
                        mView.registerSuccess();
                    }

                    @Override
                    public void otherError(String error) {
                        super.otherError(error);
                        mView.setAccountErrorView(error);
                        mView.requestFocus(true);
                    }
                })


        );
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

}
