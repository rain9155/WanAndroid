package com.example.hy.wanandroid.presenter.navigation;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.navigation.NavigationContract;
import com.example.hy.wanandroid.model.navigation.NavigationModel;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.DefaultObserver;
import com.example.hy.wanandroid.network.entity.navigation.Tag;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 陈健宇 at 2018/10/31
 */
public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    private NavigationContract.Model mNavigationModel;

    @Inject
    public NavigationPresenter(NavigationModel navigationModel) {
        mNavigationModel = navigationModel;
    }

    @Override
    public void loadTags() {
        addSubcriber(mNavigationModel.getTags().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultObserver<BaseResponse<List<Tag>>>() {
                    @Override
                    public void onNext(BaseResponse<List<Tag>> listBaseResponse) {
                        mView.showTags(listBaseResponse.getData());
                        List<String> tagsName = new ArrayList<>();
                        for(Tag tag : listBaseResponse.getData()){
                            tagsName.add(tag.getName());
                        }
                        mView.showTagsName(tagsName);
                    }
                }));
    }
}