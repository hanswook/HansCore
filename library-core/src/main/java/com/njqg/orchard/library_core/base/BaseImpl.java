package com.njqg.orchard.library_core.base;

import android.view.View;

import io.reactivex.disposables.Disposable;

/**
 * Created by hans
 * date: 2017/11/23 17:22.
 * e-mail: hxxx1992@163.com
 */

public interface BaseImpl {
    /**
     * 显示ProgressDialog
     */
    void showProgress();

    /**
     * 显示ProgressDialog
     *
     */
    void showProgress(String msg);

    /**
     * 取消ProgressDialog
     */
    void dismissProgress();

    /**
     *
     * @param content   内容
     * @param confirm   确定键文字
     * @param cancel    取消键文字
     * @param confirmListener   确定键监听
     * @param cancelListener    取消键监听
     */
    void showTwoButtonDialog(String content, String confirm, String cancel,
                             View.OnClickListener confirmListener,
                             View.OnClickListener cancelListener);
    /**
     * @param content   内容
     * @param confirm   确定键文字
     * @param cancel    取消键文字
     * @param confirmColor  确定键颜色
     * @param cancelColor   取消键颜色
     * @param confirmListener   确定键监听
     * @param cancelListener    取消键监听
     */
    void showTwoButtonDialog(String content, String confirm, String cancel,
                             int confirmColor, int cancelColor,
                             View.OnClickListener confirmListener,
                             View.OnClickListener cancelListener);
    /**
     *
     * @param content   内容
     * @param confirm   按钮文字
     * @param confirmListener   按钮监听
     */
    void showOneButtonDialog(String content, String confirm, View.OnClickListener confirmListener);


    boolean addRxStop(Disposable disposable);

    boolean addRxDestroy(Disposable disposable);

    void remove(Disposable disposable);


    /*View createDialog(Integer dialogLayoutRes, boolean cancelTouchOutside);
    void showDialog();
    void dismissDialog();*/
}
