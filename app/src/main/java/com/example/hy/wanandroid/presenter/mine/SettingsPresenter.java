package com.example.hy.wanandroid.presenter.mine;

import com.example.hy.wanandroid.base.presenter.BaseActivityPresenter;
import com.example.hy.wanandroid.entity.Apk;
import com.example.hy.wanandroid.event.ThemeEvent;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.contract.mine.SettingsContract;
import com.example.hy.wanandroid.event.ClearCacheEvent;
import com.example.hy.wanandroid.event.LanguageEvent;
import com.example.hy.wanandroid.event.NoImageEvent;
import com.example.hy.wanandroid.event.StatusBarEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.entity.Version;
import com.example.hy.wanandroid.utlis.FileUtil;
import com.example.hy.wanandroid.utlis.RxUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Settings的Presenter
 * Created by 陈健宇 at 2018/11/26
 */
public class SettingsPresenter extends BaseActivityPresenter<SettingsContract.View> implements SettingsContract.Presenter{

    @Inject
    public SettingsPresenter(DataModel dataModel) {
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
                RxBus.getInstance().toObservable(ClearCacheEvent.class)
                .subscribe(clearCacheEvent -> mView.clearCache())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(LanguageEvent.class)
                .subscribe(languageEvent -> {
                    mModel.setSelectedLanguage(languageEvent.getLanguage());
                    mView.handleLanguageChange();
                })
        );

        addSubscriber(
                RxBus.getInstance().toObservable(ThemeEvent.class)
                .subscribe(themeEvent -> {
                    mModel.setSelectedTheme(themeEvent.getTheme());
                    mView.handleThemeChange();
                })
        );
    }

    @Override
    public void setNoImageState(boolean isNoImage) {
        mModel.setNoImageState(isNoImage);
        RxBus.getInstance().post(new NoImageEvent());
    }

    @Override
    public void setAutoCacheState(boolean isAuto) {
        mModel.setAutoCacheState(isAuto);
    }

    @Override
    public void setStatusBarState(boolean isStatusBar) {
        mModel.setStatusBarState(isStatusBar);
        RxBus.getInstance().post(new StatusBarEvent(isStatusBar));
    }

    @Override
    public void setAutoUpdateState(boolean isAutoUpdate) {
        mModel.setAutoUpdateState(isAutoUpdate);
    }

    @Override
    public boolean getNoImageState() {
        return mModel.getNoImageState();
    }

    @Override
    public boolean getAutoCacheState() {
        return mModel.getAutoCacheState();
    }

    @Override
    public boolean getStatusBarState() {
        return mModel.getStatusBarState();
    }

    @Override
    public boolean getAutoUpdateState() {
        return mModel.getAutoUpdateState();
    }

    @Override
    public String getSelectedLanguage() {
        return mModel.getSelectedLanguage();
    }

    @Override
    public String getSelectedTheme() {
        return mModel.getSelectedTheme();
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
