package com.example.hy.wanandroid.model.mine;

import com.example.hy.wanandroid.contract.mine.CollectionContract;
import com.example.hy.wanandroid.core.network.api.MineApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;
import com.example.hy.wanandroid.core.network.entity.mine.CollectionRequest;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Collection的Model
 * Created by 陈健宇 at 2018/11/22
 */
public class CollectionModel implements CollectionContract.Model {

    private MineApis mMineApis;

    @Inject
    public CollectionModel(MineApis mineApis) {
        mMineApis = mineApis;
    }


    @Override
    public Observable<BaseResponse<CollectionRequest>> getCollectionRequest(int pageNum) {
        return mMineApis.getCollectionRequest(pageNum);
    }

    @Override
    public Observable<BaseResponse<Collection>> getUnCollectRequest(int id, int originalId) {
        return mMineApis.getUnCollectionRequest(id, originalId);
    }
}
