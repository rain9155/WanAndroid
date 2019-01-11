package com.example.hy.wanandroid.view.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlib.utils.IntentUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseMvpFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.mine.MineContract;
import com.example.hy.wanandroid.di.module.fragment.MineFragmentModule;
import com.example.hy.wanandroid.presenter.mine.MinePresenter;
import com.example.commonlib.utils.AnimUtil;
import com.example.commonlib.utils.StatusBarUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.widget.dialog.ChangeFaceDialog;
import com.example.hy.wanandroid.widget.dialog.LogoutDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import dagger.Lazy;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * 我的tab
 * Created by 陈健宇 at 2018/10/23
 */
public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.View {

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
    @BindView(R.id.srl_mine)
    SmartRefreshLayout srlMine;
    @BindView(R.id.cl_collection)
    ConstraintLayout clCollection;
    @BindView(R.id.cl_settings)
    ConstraintLayout clSettings;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.cl_logout)
    ConstraintLayout clLogout;

    @Inject
    MinePresenter mPresenter;
    @Inject
    Lazy<LogoutDialog> mLogoutDialog;
    @Inject
    Lazy<ChangeFaceDialog> mChangeFaceDialog;

    private Uri mCropImageUri;
    private boolean isChangeFace;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void inject() {
        if (!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getComponent().getMineFragmentComponent(new MineFragmentModule()).inject(this);
    }

    @Override
    protected MinePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initView() {
        super.initView();
        if (User.getInstance().isLoginStatus()) {
            showLoginView();
        } else {
            showLogoutView();
        }

        if (mPresenter.getNightModeState())
            ivBack.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        ivFace.setOnClickListener(v -> mChangeFaceDialog.get().show(getChildFragmentManager(), ChangeFaceDialog.class.getSimpleName()));
        btnLogin.setOnClickListener(v -> LoginActivity.startActivity(mActivity));

        clSettings.setOnClickListener(v -> SettingsActivity.startActivity(mActivity));
        clCollection.setOnClickListener(v -> {
            if (!User.getInstance().isLoginStatus()) {
                LoginActivity.startActivityForResultByFragment(mActivity, this, Constant.REQUEST_SHOW_COLLECTIONS);
                showToast(getString(R.string.first_login));
            } else
                CollectionActivity.startActivity(mActivity);
        });
        clAboutus.setOnClickListener(v -> AboutUsActivity.startActivity(mActivity));
        clLogout.setOnClickListener(v -> mLogoutDialog.get().show(getChildFragmentManager(), LogoutDialog.class.getSimpleName()));
    }

    @Override
    protected void loadData() {
        mPresenter.subscribleEvent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mLogoutDialog != null) mLogoutDialog = null;
        if(mChangeFaceDialog != null) mChangeFaceDialog = null;
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) return;

        //handle result of collection Activity
        if(requestCode == Constant.REQUEST_SHOW_COLLECTIONS)
            CollectionActivity.startActivity(mActivity);

        // handle result of pick image chooser
        if(requestCode == Constant.REQUEST_PICK_IMAGE_CHOOSER){
            Uri imageUri = CropImage.getPickImageResultUri(mActivity, data);
            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(mActivity, imageUri)) {
                mCropImageUri = imageUri;
                ActivityCompat.requestPermissions(
                        mActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                CropImage.activity(imageUri).start(mActivity);
                CropperImageActivity.startActivityByFragment(mActivity, this, imageUri, isChangeFace);
            }
        }

        //handle result of Cropper Activity
        if (requestCode == Constant.REQUEST_CROP_IMAGE_ACTIVITY) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri resultUri = result.getUri();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver().openInputStream(resultUri));
                if(isChangeFace)
                    ivFace.setImageBitmap(bitmap);
                else
                    ivBack.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE){
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                // required permissions granted, start crop image activity
                //CropImage.activity(mCropImageUri).start(mActivity);
                CropperImageActivity.startActivityByFragment(mActivity, this, mCropImageUri, isChangeFace);
            else
                showToast(getString(R.string.mineFragment_permissions_denied));
        }

    }

    @Override
    public void useNightNode(boolean isNight) {
        if (isNight)
            ivBack.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        else
            ivBack.getDrawable().clearColorFilter();
    }

    @Override
    public void setStatusBarColor(boolean isSet) {
        StatusBarUtil.immersiveInFragments(mActivity, Color.TRANSPARENT, 1);
    }

    @Override
    public void showLoginView() {
        AnimUtil.hideByAlpha(btnLogin);
        AnimUtil.showByAlpha(tvUsername);
        AnimUtil.showByAlpha(rlLogout);
        tvUsername.setText(User.getInstance().getUsername());
    }

    @Override
    public void showLogoutView() {
        AnimUtil.hideByAlpha(rlLogout);
        AnimUtil.hideByAlpha(tvUsername);
        AnimUtil.showByAlpha(btnLogin);
    }

    @Override
    public void changeFaceOrBackground(boolean isChangeFace) {
        this.isChangeFace = isChangeFace;

        List<Intent> allIntents = new ArrayList<>();
        Intent chooserIntent;
        Intent targerIntent;

        //add All Camera Intents
        allIntents.addAll(IntentUtil.getCameraIntents(mActivity));

        //add All gallery Intents
        List<Intent> allGalleryIntents = IntentUtil.getGalleryIntents(mActivity, Intent.ACTION_GET_CONTENT, true);
        //部分机型会因为用用Intent.ACTION_GET_CONTENT获不到intents，用Intent.ACTION_PICK
        if(allGalleryIntents.isEmpty())
            allGalleryIntents = IntentUtil.getGalleryIntents(mActivity, Intent.ACTION_PICK, true);
        allIntents.addAll(allGalleryIntents);

        //create chooserIntent
        if(allIntents.isEmpty()){
            targerIntent = new Intent();
        }else {
            targerIntent = allIntents.get(allIntents.size() - 1);
            allIntents.remove(allIntents.size() - 1);
        }
        chooserIntent = Intent.createChooser(targerIntent, getString(R.string.mineFragment_choose_intent));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        startActivityForResult(chooserIntent, Constant.REQUEST_PICK_IMAGE_CHOOSER);
       // CropImage.startPickImageActivity(mActivity);
    }

    public static MineFragment newInstance() {
        return new MineFragment();
    }
}
