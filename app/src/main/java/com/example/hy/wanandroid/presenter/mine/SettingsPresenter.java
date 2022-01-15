package com.example.hy.wanandroid.presenter.mine;

import android.content.res.Resources;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BaseActivityPresenter;
import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.contract.mine.SettingsContract;
import com.example.hy.wanandroid.event.ClearCacheEvent;
import com.example.hy.wanandroid.event.LanguageEvent;
import com.example.hy.wanandroid.event.NightModeEvent;
import com.example.hy.wanandroid.event.NoImageEvent;
import com.example.hy.wanandroid.event.StatusBarEvent;
import com.example.hy.wanandroid.event.UpdataEvent;
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

    private boolean isUpdata = false;

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
                RxBus.getInstance().toObservable(UpdataEvent.class)
                        .filter(new Predicate<UpdataEvent>() {
                            @Override
                            public boolean test(UpdataEvent updataEvent) throws Exception {
                                return updataEvent.isMain() == false;
                            }
                        })
                        .subscribe(updataEvent -> mView.upDataVersion())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(ClearCacheEvent.class)
                .subscribe(clearCacheEvent -> mView.clearCache())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(LanguageEvent.class)
                .subscribe(languageEvent -> {
                    mModel.setSelectedLanguage(languageEvent.getLanguage());
                    mView.handleLanguage();
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
    public void setNightModeState(boolean isNight) {
        mModel.setNightModeState(isNight);
        RxBus.getInstance().post(new NightModeEvent(isNight));
    }

    @Override
    public void setStatusBarState(boolean isStatusBar) {
        mModel.setStatusBarState(isStatusBar);
        RxBus.getInstance().post(new StatusBarEvent(isStatusBar));
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
    public boolean getNightModeState() {
        return mModel.getNightModeState();
    }

    @Override
    public boolean getStatusBarState() {
        return mModel.getStatusBarState();
    }

    @Override
    public boolean getAutoUpdataState() {
        return mModel.getAutoUpdataState();
    }

    @Override
    public String getSelectedLanguage() {
        return mModel.getSelectedLanguage();
    }

    @Override
    public void checkVersion(String currentVersion) {
        addSubscriber(
                mModel.getVersionDetails()
                .compose(RxUtils.switchSchedulers())
                .filter(new Predicate<Version>() {
                    @Override
                    public boolean test(Version version) throws Exception {
                        isUpdata = Float.valueOf(currentVersion.replace("v", "")) < Float.valueOf(version.getTag_name().replace("v", ""));
                        return isUpdata;
                    }
                })
                .map(new Function<Version, String>() {
                    @Override
                    public String apply(Version version) throws Exception {
                        StringBuilder content = new StringBuilder();
                        Resources resources = App.getContext().getResources();
                        mView.setNewVersionName(version.getTag_name());
                        content.append(resources.getString(R.string.dialog_versionName)).append(version.getTag_name()).append("\n")
                                .append(resources.getString(R.string.dialog_versionSize)).append(FileUtil.getFormatSize(version.getAssets().get(0).getSize())).append("\n")
                                .append(resources.getString(R.string.dialog_versionContent)).append("\n").append(version.getBody());
                        return content.toString();
                    }
                })
                .subscribeWith(new DefaultObserver<String>(mView, true, true){
                    @Override
                    public void onNext(String s){
                        super.onNext(s);
                        mView.showUpdataDialog(s);
                    }
                    @Override
                    public void onComplete() {
                        if(!isUpdata)
                            mView.showAlreadyNewToast(App.getContext().getResources().getString(R.string.dialog_version_already));
                    }
                })
        );
    }

}
