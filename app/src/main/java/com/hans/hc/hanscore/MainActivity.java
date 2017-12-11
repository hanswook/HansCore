package com.hans.hc.hanscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hans.hc.hanscore.libpack.BaseActivity;
import com.hans.hc.hanscore.libpack.RetrofitManager;
import com.njqg.orchard.library_core.net.CommonSubscriber;
import com.njqg.orchard.library_core.utils.LogUtils;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {


    @Override
    protected void init() {
        RetrofitManager.getInstance().create(ApiService.class).getGankData().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CommonSubscriber<GankB>(context) {
                    @Override
                    public void onNext(GankB gankB) {
                        LogUtils.e(TAG,"gankb:"+gankB.getResults().get(0).getDesc());
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
