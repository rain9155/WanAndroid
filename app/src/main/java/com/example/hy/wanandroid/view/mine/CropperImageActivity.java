package com.example.hy.wanandroid.view.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlib.utils.StatusBarUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.config.Constant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class CropperImageActivity extends BaseActivity implements CropImageView.OnCropImageCompleteListener {

    @BindView(R.id.cropImageView)
    CropImageView cropImageView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_crop)
    ImageView ivCrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Uri mCropImageUri;
    private CropImageOptions mOptions;
    private int isChangeFace;

    @Override
    protected void inject() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getBundleExtra(Constant.KEY_CROP_BUNDLE);
        mCropImageUri = bundle.getParcelable(Constant.KEY_IMAGE_URI);
        mOptions = bundle.getParcelable(Constant.KEY_IMAGE_OPTIONS);
        isChangeFace = bundle.getInt(Constant.KEY_IS_FACE);
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            cropImageView.setImageUriAsync(mCropImageUri);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_copper_imager;
    }

    @Override
    protected void initView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setHeightAndPadding(this, toolbar);
        toolbar.setNavigationOnClickListener(v -> setResultCancel());
        ivCrop.setOnClickListener(v -> {
            Uri outputUri = getOutputUri();
            cropImageView.saveCroppedImageAsync(
                    outputUri,
                    mOptions.outputCompressFormat,
                    mOptions.outputCompressQuality,
                    mOptions.outputRequestWidth,
                    mOptions.outputRequestHeight,
                    mOptions.outputRequestSizeOptions);
        });
        cropImageView.setAutoZoomEnabled(true);
        if(isChangeFace == Constant.CHANGE_FACE) cropImageView.setCropShape(CropImageView.CropShape.OVAL);
        else cropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResultCancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cropImageView.setOnCropImageCompleteListener(null);
    }


    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        setResultOK(result.getUri(), result.getError(), result.getSampleSize());
    }

    protected void setResultOK(Uri uri, Exception error, int sampleSize) {
        int resultCode = error == null ? RESULT_OK : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
        setResult(resultCode, getResultIntent(uri, error, sampleSize));
        finish();
    }

    protected void setResultCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    protected Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
        CropImage.ActivityResult result =
                new CropImage.ActivityResult(
                        cropImageView.getImageUri(),
                        uri,
                        error,
                        cropImageView.getCropPoints(),
                        cropImageView.getCropRect(),
                        cropImageView.getRotatedDegrees(),
                        cropImageView.getWholeImageRect(),
                        sampleSize);
        Intent intent = new Intent();
        intent.putExtras(getIntent());
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, result);
        return intent;
    }


    /**
     * 裁剪完成的图片的输出uri
     */
    protected Uri getOutputUri() {
        Uri outputUri = mOptions.outputUri;
        if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
            try {
                String ext = mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG
                                ? ".jpg"
                                : mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
                outputUri = Uri.fromFile(File.createTempFile("cropped", ext, getCacheDir()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file for output image", e);
            }
        }
        return outputUri;
    }

    /**
     * 通过Fragment启动Activity
     * @param uri 图片uri
     * @param isFace 是否改变背景图片
     */
    public static void startActivityByFragment(Activity activity, Fragment fragment, Uri uri, int isFace) {
        Intent intent = new Intent(activity, CropperImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.KEY_IMAGE_URI, uri);
        bundle.putParcelable(Constant.KEY_IMAGE_OPTIONS, new CropImageOptions());
        bundle.putInt(Constant.KEY_IS_FACE, isFace);
        intent.putExtra(Constant.KEY_CROP_BUNDLE, bundle);
        fragment.startActivityForResult(intent, Constant.REQUEST_CROP_IMAGE_ACTIVITY);
    }
}
