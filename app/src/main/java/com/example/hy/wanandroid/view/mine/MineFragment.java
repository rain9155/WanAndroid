package com.example.hy.wanandroid.view.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.mine.MineContract;
import com.example.hy.wanandroid.di.module.fragment.MineFragmentModule;
import com.example.hy.wanandroid.event.LoginEvent;
import com.example.hy.wanandroid.presenter.mine.MinePresenter;
import com.example.hy.wanandroid.utils.StatusBarUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.utilslibrary.AnimUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.lang.reflect.Field;

import javax.inject.Inject;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的tab
 * Created by 陈健宇 at 2018/10/23
 */
public class MineFragment extends BaseFragment implements MineContract.View {

    @BindView(R.id.iv_face)
    CircleImageView ivFace;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.iv_about_us)
    ImageView ivAboutUs;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.cl_about_us)
    ConstraintLayout clAboutus;
    @BindView(R.id.iv_logout)
    ImageView ivLogout;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.cl_logout)
    ConstraintLayout clLogout;
    @BindView(R.id.srl_mine)
    SmartRefreshLayout srlMine;
    @BindView(R.id.cl_collection)
    ConstraintLayout clCollection;
    @BindView(R.id.cl_settings)
    ConstraintLayout clSettings;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Inject
    MinePresenter mPresenter;

    private AlertDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        if (!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getComponent().getMineFragmentComponent(new MineFragmentModule()).inject(this);
        mPresenter.attachView(this);

        if (User.getInstance().isLoginStatus()) {
            showLoginView();
        } else {
            showLogoutView();
        }

        if(mPresenter.getNightModeState()) ivBack.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        btnLogin.setOnClickListener(v -> LoginActivity.startActivity(_mActivity));
        clSettings.setOnClickListener(v -> SettingsActivity.startActivity(_mActivity));
        clCollection.setOnClickListener(v -> {
            if (!User.getInstance().isLoginStatus()) {
                LoginActivity.startActivityForResultByFragment(_mActivity, this, Constant.REQUEST_SHOW_COLLECTIONS);
                showToast(getString(R.string.first_login));
            } else
                CollectionActivity.startActivity(_mActivity);
        });
        clAboutus.setOnClickListener(v -> AboutUsActivity.startActivity(_mActivity));
        clLogout.setOnClickListener(v -> showDialog());
    }

    @Override
    protected void loadData() {
        mPresenter.subscribleEvent();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constant.REQUEST_SHOW_COLLECTIONS)
            CollectionActivity.startActivity(_mActivity);
    }

    @Override
    public void userNightNode(boolean isNight) {
        super.userNightNode(isNight);
        if (isNight) ivBack.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        else  ivBack.getDrawable().clearColorFilter();
    }

    @Override
    public void setStatusBarColor(boolean isSet) {
        StatusBarUtil.immersiveForImage(_mActivity, Color.TRANSPARENT, 1);
    }

    @Override
    public void showLoginView() {
        AnimUtil.hideByAlpha(btnLogin);
        AnimUtil.showByAlpha(tvUsername);
        AnimUtil.showByAlpha(clLogout);
        tvUsername.setText(User.getInstance().getUsername());
    }

    @Override
    public void showLogoutView() {
        AnimUtil.hideByAlpha(clLogout);
        AnimUtil.hideByAlpha(tvUsername);
        AnimUtil.showByAlpha(btnLogin);
    }

    /**
     * 显示Dialog
     */
    public void showDialog() {
        dialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialog)
                .setCancelable(false)
                .setIcon(R.drawable.ic_toast)
                .setTitle(R.string.dialog_logout_toast)
                .setMessage(R.string.dialog_confirm_logout)
                .setNegativeButton(R.string.dialog_logout_cancel, (dialog1, which) -> dialog.dismiss())
                .setPositiveButton(R.string.dialog_logout_confirm, (dialog1, which) -> {
                    dialog.dismiss();
                    RxBus.getInstance().post(new LoginEvent(false));
                })
                .create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.setOnKeyListener((dialog1, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
        dialog.show();
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            //通过反射修改title字体大小和颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(20);
            mTitleView.setTextColor(getResources().getColor(R.color.primaryText));
            //通过反射修改message字体大小和颜色
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextSize(18);
            mMessageView.setPadding(80, 50, 0, 0);
            mMessageView.setTextColor(getResources().getColor(R.color.primaryText));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.primaryText));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.primaryText));
    }

    public static MineFragment newInstance() {

        return new MineFragment();
    }
}
