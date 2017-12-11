package com.njqg.orchard.library_core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njqg.orchard.library_core.R;
import com.njqg.orchard.library_core.utils.Constant;
import com.njqg.orchard.library_core.view.CustomDialog;


public abstract class BaseLibActivity extends BaseRxActivity {

    protected Context context;
    protected String TAG;
    //把父类activity和子类activity的view都add到这里
    private LinearLayout parentLinearLayout;

    private TextView mToolbarTitle;

    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    //  对话框
    private CustomDialog dialog;
    //  对话框布局的View
    private View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initContentView(R.layout.activity_base);
        //  注入子Activity布局
        if (getLayoutId() > 0) {
            TAG = getClass().getSimpleName();
            setContentView(getLayoutId());
            butterKnifeInit();
            initToolBar();
        }
        //TODO 修改为在子类中控制，不适用常量
        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        init();
    }

    protected abstract void butterKnifeInit();


    protected abstract void init();


    public abstract int getLayoutId();

    private void initToolBar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
        mToolbarSubTitle.setVisibility(View.GONE);
         /*
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("Sub Title");
        */
        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if (null != getToolbar() && isShowBacking()) {
            showBack();
        }
    }

    protected boolean isFullScreen() {
        return false;
    }


/******************************************* TollBar相关 ******************************************************/
    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getSubTitle() {
        return mToolbarSubTitle;
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }


    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.drawable.icon_back);
        getToolbar().setNavigationOnClickListener((v) -> onBackPressed());
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }
    /******************************************* TollBar相关结束 ******************************************************/


    /**
     * 初始化contentiew
     */
    private void initContentView(@LayoutRes int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        //  将BaseActivity的布局添加到parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    /**
     * 重写setContentView方法，子类Activity会调用该方法
     *
     * @param layoutResID 子类布局文件的id
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //  将子类布局添加到parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
//        ButterKnife.bind(this);

    }

    @Override
    public void setContentView(View view) {
        parentLinearLayout.addView(view);
//        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentLinearLayout.addView(view, params);
//        ButterKnife.bind(this);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param color    color xml文件下的颜色
     */
    public void setStatusBarColor(Activity activity, int color) {
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }*/
        if (Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(color);
        }
    }


    /**
     * @param content         内容
     * @param confirm         确定键文字
     * @param cancel          取消键文字
     * @param confirmListener 确定键监听
     * @param cancelListener  取消键监听
     */
    @Override
    public void showTwoButtonDialog(String content, String confirm, String cancel,
                                    View.OnClickListener confirmListener,
                                    View.OnClickListener cancelListener) {
        dialog = new CustomDialog.Builder(this)
                .setTheme(R.style.IdeaDialog)
                .setContent(content)
                .addConfirmClickListener(confirm, confirmListener)
                .addCancelClickListener(cancel, cancelListener)
                .build();
        dialog.show();
    }

    /**
     * @param content         内容
     * @param confirm         确定键文字
     * @param cancel          取消键文字
     * @param confirmColor    确定键颜色
     * @param cancelColor     取消键颜色
     * @param confirmListener 确定键监听
     * @param cancelListener  取消键监听
     */
    @Override
    public void showTwoButtonDialog(String content, String confirm, String cancel,
                                    @ColorInt int confirmColor, @ColorInt int cancelColor,
                                    View.OnClickListener confirmListener,
                                    View.OnClickListener cancelListener) {
        dialog = new CustomDialog.Builder(this)
                .setTheme(R.style.IdeaDialog)
                .setContent(content)
                .setConfirmColor(confirmColor)
                .setCancelColor(cancelColor)
                .addConfirmClickListener(confirm, confirmListener)
                .addCancelClickListener(cancel, cancelListener)
                .build();
        dialog.show();
    }

    /**
     * @param content         内容
     * @param confirm         按钮文字
     * @param confirmListener 按钮监听
     */
    @Override
    public void showOneButtonDialog(String content, String confirm, View.OnClickListener confirmListener) {
        dialog = new CustomDialog.Builder(this)
                .setTheme(R.style.IdeaDialog)
                .setContent(content)
                .addConfirmClickListener(confirm, confirmListener)
                .showOneButton()
                .build();
        dialog.show();
    }


    /**
     * create custom dialog
     * 可以定制任意的dialog样式
     *
     * @param dialogLayoutRes    dialog布局资源文件
     * @param cancelTouchOutside 点击外部是否可以取消
     * @return
     */
    public View createDialog(@LayoutRes Integer dialogLayoutRes, boolean cancelTouchOutside) {
        if (dialogLayoutRes == null) {
            dialogLayoutRes = R.layout.custom_dialog;
        }
        dialogView = LayoutInflater.from(this).inflate(dialogLayoutRes, null);
        //  计算dialog宽高
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        dialogView.measure(measureSpec, measureSpec);
        int height = dialogView.getMeasuredHeight();
        int width = dialogView.getMeasuredWidth();

        dialog = new CustomDialog.Builder(this)
                .setTheme(R.style.IdeaDialog)
                .setHeightPx(height)
                .setWidthPx(width)
                .cancelTouchOutside(cancelTouchOutside)
                .setDialogLayout(dialogView).build();
        return dialogView;
    }

    /**
     * 显示dialog
     */
    public void showDialog() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 隐藏dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    /**
     * 设置系统标题栏的透明度
     *
     * @param activity
     * @param on
     */
    protected void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



}
