package com.example.hy.wanandroid.presenter;

import android.content.res.Resources;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BaseActivityPresenter;
import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.entity.Apk;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.contract.MainContract;
import com.example.hy.wanandroid.event.InstallApkEvent;
import com.example.hy.wanandroid.event.OpenBrowseEvent;
import com.example.hy.wanandroid.event.StatusBarEvent;
import com.example.hy.wanandroid.event.UpdateEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.entity.Version;
import com.example.hy.wanandroid.utlis.FileUtil;
import com.example.hy.wanandroid.utlis.RxUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * MainActivity的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class MainPresenter extends BaseActivityPresenter<MainContract.View> implements MainContract.Presenter{

    @Inject
    public MainPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void subscribeEvent() {
        super.subscribeEvent();

        addSubscriber(
                RxBus.getInstance().toObservable(StatusBarEvent.class)
                        .compose(RxUtils.switchSchedulers())
                        .subscribe(statusBarEvent -> mView.setStatusBarColor(statusBarEvent.isSet()))
        );


        addSubscriber(
                RxBus.getInstance().toObservable(UpdateEvent.class)
                .subscribe(updateEvent -> mView.updateVersion(updateEvent.getApk()))
        );

        addSubscriber(
                RxBus.getInstance().toObservable(OpenBrowseEvent.class)
                .subscribe(openBrowseEvent -> mView.showOpenBrowseDialog(openBrowseEvent.getApkUrl()))
        );

        addSubscriber(
                RxBus.getInstance().toObservable(InstallApkEvent.class)
                .subscribe(installApkEvent -> mView.installApk(installApkEvent.getApkUri()))
        );
    }

    @Override
    public void setCurrentItem(int position) {
        mModel.setCurMainItem(position);
    }

    @Override
    public int getCurrentItem() {
        return mModel.getCurMainItem();
    }

    @Override
    public boolean getAutoUpdateState() {
        return mModel.getAutoUpdateState();
    }

    @Override
    public void checkVersion(String currentVersion) {
        addSubscriber(
            mModel.getVersionDetails()
                .compose(RxUtils.switchSchedulers())
                .filter(new Predicate<Version>() {
                    @Override
                    public boolean test(Version version) throws Exception {
                        if(version == null || version.getAssets().size() == 0) {
                            throw new IllegalArgumentException("version assets is empty!");
                        }
                        return true;
                    }
                })
                .map(new Function<Version, Apk>() {
                    @Override
                    public Apk apply(Version version) throws Exception {
                        Version.AssetsBean asset = version.getAssets().get(0);
                        String downloadUrl = asset.getBrowser_download_url();
                        String size = FileUtil.getFormatSize(asset.getSize());
                        String name = version.getName();
                        String versionName = version.getTag_name();
                        String versionBody = version.getBody();
                        boolean needUpdate = Float.parseFloat(currentVersion.replace("v", "")) < Float.parseFloat(versionName.replace("v", ""));
                        return new Apk(downloadUrl, name, size, versionName, versionBody, needUpdate);
                    }
                })
                .subscribeWith(new DefaultObserver<Apk>(mView, true, true){
                    @Override
                    public void onNext(Apk apk){
                        super.onNext(apk);
                        mView.showUpdateDialog(apk);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showUpdateDialog(null);
                    }
                })
        );
    }

}
