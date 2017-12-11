package com.hans.hc.hanscore.libpack;

import android.support.annotation.NonNull;
import android.view.View;

import com.njqg.orchard.library_core.base.BaseLibLazyFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hans
 * date: 2017/11/29 11:27.
 * e-mail: hxxx1992@163.com
 */

public abstract class BaseLazysFragment extends BaseLibLazyFragment {
    private Unbinder unbinder;

    @Override
    protected void butterknifeInit(@NonNull Object target, @NonNull View source) {
        unbinder = ButterKnife.bind(target, source);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}
