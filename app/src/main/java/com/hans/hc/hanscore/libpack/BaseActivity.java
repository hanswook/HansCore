package com.hans.hc.hanscore.libpack;


import com.njqg.orchard.library_core.base.BaseLibActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hans
 * date: 2017/11/29 11:13.
 * e-mail: hxxx1992@163.com
 */

public abstract class BaseActivity extends BaseLibActivity {

    protected Unbinder unbinder;

    @Override
    protected void butterKnifeInit() {
        unbinder = ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();

    }
}
