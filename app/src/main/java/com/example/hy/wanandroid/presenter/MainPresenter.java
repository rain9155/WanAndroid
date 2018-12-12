package com.example.hy.wanandroid.presenter;

import android.content.res.Resources;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.contract.MainContract;
import com.example.hy.wanandroid.event.NightModeEvent;
import com.example.hy.wanandroid.event.SettingsNightModeEvent;
import com.example.hy.wanandroid.event.StatusBarEvent;
import com.example.hy.wanandroid.event.UpdataEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Version;
import com.example.hy.wanandroid.utils.FileUtil;
import com.example.hy.wanandroid.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * MainActivity的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter{

    @Inject
    public MainPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void subscribleEvent() {
        super.subscribleEvent();

        addSubcriber(
                RxBus.getInstance().toObservable(StatusBarEvent.class)
                        .compose(RxUtils.switchSchedulers())
                        .subscribe(statusBarEvent -> mView.setStatusBarColor(statusBarEvent.isSet()))
        );

        addSubcriber(
                RxBus.getInstance().toObservable(NightModeEvent.class)
                        .compose(RxUtils.switchSchedulers())
                        .subscribeWith(new DefaultObserver<NightModeEvent>(mView, false, false){
                            @Override
                            public void onNext(NightModeEvent nightModeEvent) {
                                mView.useNightNode(nightModeEvent.isNight());
                                RxBus.getInstance().post(new SettingsNightModeEvent(nightModeEvent.isNight()));
                            }

                            @Override
                            protected void unknown() {
                                mView.showToast(App.getContext().getString(R.string.error_switch_fail));
                            }
                        })
        );

        addSubcriber(
                RxBus.getInstance().toObservable(UpdataEvent.class)
                .filter(new Predicate<UpdataEvent>() {
                    @Override
                    public boolean test(UpdataEvent updataEvent) throws Exception {
                        return updataEvent.isMain();
                    }
                })
                .subscribe(updataEvent -> mView.upDataVersion())
        );
    }

    @Override
    public void setCurrentItem(int position) {
        mModel.setCurrentItem(position);
    }

    @Override
    public int getCurrentItem() {
        return mModel.getCurrentItem();
    }

    @Override
    public void checkVersion(String currentVersion) {
        addSubcriber(
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
                                        .append(resources.getString(R.string.dialog_versionContent)).append(version.getBody());
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
