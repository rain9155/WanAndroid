package com.example.hy.wanandroid.presenter.navigation;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.navigation.NavigationContract;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Tag;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by 陈健宇 at 2018/10/31
 */
public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    @Inject
    public NavigationPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void loadTags() {
        addSubcriber(
                mModel.getTags()
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<List<Tag>>(mView) {
                    @Override
                    public void onNext(List<Tag> tags) {
                        super.onNext(tags);
                        mView.showTags(tags);
                        List<String> tagsName = new ArrayList<>();
                        for(Tag tag : tags){
                            tagsName.add(tag.getName());
                        }
                        mView.showTagsName(tagsName);
                    }
                }));
    }
}
