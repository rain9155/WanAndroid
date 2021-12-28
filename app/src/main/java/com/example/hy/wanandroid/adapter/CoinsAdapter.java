package com.example.hy.wanandroid.adapter;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.entity.Coin;
import com.example.hy.wanandroid.utlis.TimeUtil;

import java.util.List;

/**
 * 个人积分Adapter
 * Created by 陈健宇 at 2019/9/21
 */
public class CoinsAdapter extends BaseQuickAdapter<Coin, BaseViewHolder>{


    public CoinsAdapter(int layoutResId, @Nullable List<Coin> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Coin item) {
        if(item == null) return;
        String coin = item.getDesc().substring(item.getDesc().lastIndexOf('：') + 1).trim();
        String spanCoin = "+ " + getSum(coin);
        SpannableStringBuilder spanCoinStr = new SpannableStringBuilder(spanCoin);
        spanCoinStr.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.colorAddCoin)),
                0, spanCoin.length(),
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        helper.setText(R.id.tv_reason, item.getReason())
                .setText(R.id.tv_coin, spanCoinStr)
                .setText(R.id.tv_desc1, "时间：" + TimeUtil.stampToDate(item.getDate()))
                .setText(R.id.tv_desc2, "积分：" + coin);
    }

    private int getSum(String expression){
        String[] strings = expression.split(" ");
        return Integer.valueOf(strings[0]) + Integer.valueOf(strings[strings.length - 1]);
    }

}
