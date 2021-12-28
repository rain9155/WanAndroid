package com.example.hy.wanandroid.presenter;

import android.content.res.Resources;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BaseActivityPresenter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.contract.MainContract;
import com.example.hy.wanandroid.event.InstallApkEvent;
import com.example.hy.wanandroid.event.OpenBrowseEvent;
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
                RxBus.getInstance().toObservable(UpdataEvent.class)
                .filter(new Predicate<UpdataEvent>() {
                    @Override
                    public boolean test(UpdataEvent updataEvent) throws Exception {
                        return updataEvent.isMain();
                    }
                })
                .subscribe(updataEvent -> mView.upDataVersion())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(OpenBrowseEvent.class)
                .subscribe(openBrowseEvent -> mView.showOpenBrowseDialog())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(InstallApkEvent.class)
                .subscribe(installApkEvent -> mView.installApk())
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
    public void checkVersion(String currentVersion) {
        addSubscriber(
                mModel.getVersionDetails()
                        .compose(RxUtils.switchSchedulers())
                        .filter(new Predicate<Version>() {
                            @Override
                            public boolean test(Version version) throws Exception {
                                return Float.valueOf(currentVersion.replace("v", "")) < Float.valueOf(version.getTag_name().replace("v", ""));
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
                        })
        );
    }

}
