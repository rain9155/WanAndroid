package com.example.hy.wanandroid.base.fragment;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by 陈健宇 at 2019/1/1
 */
public class BaseDialogFragment extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
        if(!isStateSaved()) return;
        super.show(manager, tag);
    }
}
