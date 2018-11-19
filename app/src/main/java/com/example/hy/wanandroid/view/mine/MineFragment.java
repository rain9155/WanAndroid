package com.example.hy.wanandroid.view.mine;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.mine.MineContract;
import com.example.hy.wanandroid.di.module.fragment.MineFragmentModule;
import com.example.hy.wanandroid.presenter.mine.MinePresenter;
import com.example.hy.wanandroid.utils.AnimUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.widget.dialog.LogoutDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
    @BindView(R.id.cl_1)
    ConstraintLayout cl1;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.cl_2)
    ConstraintLayout cl2;
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

    @Inject
    MinePresenter mPresenter;
    @Inject
    LogoutDialog mLogoutDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        if (!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getComponent().getMineFragmentComponent(new MineFragmentModule()).inject(this);
        mPresenter.attachView(this);

        if(User.getInstance().isLoginStatus()){
            showLoginView();
        }else {
            showLogoutView();
        }

        clAboutus.setOnClickListener(v -> AboutUsActivity.startActivity(_mActivity));

        btnLogin.setOnClickListener(v -> LoginActivity.startActivity(_mActivity));
        clLogout.setOnClickListener(v -> {
            assert getFragmentManager() != null;
            mLogoutDialog.show(getFragmentManager(), "tag2");
        });
    }

    @Override
    protected void loadData(){
        mPresenter.subscribleEvent();
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
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

    public static MineFragment newInstance(){
        return new MineFragment();
    }
}
