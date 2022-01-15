package com.example.hy.wanandroid.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.entity.UserCoin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 积分排行榜Adapter
 * Created by 陈健宇 at 2019/9/22
 */
public class CoinsRankAdapter extends BaseQuickAdapter<UserCoin, BaseViewHolder>{

    @Inject
    public CoinsRankAdapter() {
        this(R.layout.item_coins_rank, new ArrayList<>());
    }

    public CoinsRankAdapter(int layoutResId, @Nullable List<UserCoin> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCoin item) {
        helper.setText(R.id.tv_rank, String.valueOf(helper.getLayoutPosition() + 1))
                .setText(R.id.tv_name, item.getUsername() )
                .setText(R.id.tv_coin, String.valueOf(item.getCoinCount()));
    }
}
