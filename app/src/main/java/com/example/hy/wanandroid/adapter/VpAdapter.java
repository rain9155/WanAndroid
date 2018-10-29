package com.example.hy.wanandroid.adapter;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 第二级知识体系的Vp适配器
 * Created by 陈健宇 at 2018/10/29
 */
public class VpAdapter extends FragmentPagerAdapter {

    private List<SupportFragment> mFragments;
    private List<String> mTitles;

    public VpAdapter(FragmentManager fm, List<SupportFragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
