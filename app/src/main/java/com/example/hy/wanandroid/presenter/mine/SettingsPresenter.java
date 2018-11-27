package com.example.hy.wanandroid.presenter.mine;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.contract.mine.SettingsContract;
import com.example.hy.wanandroid.event.NightModeEvent;
import com.example.hy.wanandroid.model.DataModel;

import javax.inject.Inject;

/**
 * Settings的Presenter
 * Created by 陈健宇 at 2018/11/26
 */
public class SettingsPresenter extends BasePresenter<SettingsContract.View> implements SettingsContract.Presenter{

    @Inject
    public SettingsPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void setNoImageState(boolean isNoImage) {
        mModel.setNoImageState(isNoImage);
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
}
