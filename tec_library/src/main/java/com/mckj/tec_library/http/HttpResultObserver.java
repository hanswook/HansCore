package com.mckj.tec_library.http;



import com.mckj.tec_library.base.BaseView;
import com.mckj.tec_library.utils.LogUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @title: 网络请求结果 Observer
 * @author: hans
 * @date: 2018/10/9
 * @update: 2018/10/9
 * @describe: TODO
 **/
public abstract class HttpResultObserver<T> implements Observer<T> {
    private BaseView mView;

    public HttpResultObserver(BaseView view) {
        this.mView = view;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        // 订阅中c
        mView.addRxDestroy(disposable);
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(e);
        mView.hideLoading();
        if(e != null) {
            if(e.getMessage().contains("Unable to resolve host")) {
                mView.showToast("当前网络不可用，请检查网络设置");
            } else {
                mView.showToast(e.getMessage());
            }
        }else {
            mView.showToast(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        mView.hideLoading();
    }
}
