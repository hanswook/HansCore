package com.mckj.tec_library.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.mckj.tec_library.R;


public class ToastUtil {

    private static Toast toast = null;

    public static void showToast(Context context, String text) {

        if (toast == null) {
            toast = Toast.makeText(
                    context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        int mY = DensityUtil.dip2px(24, context);
        toast.setGravity(Gravity.BOTTOM, 0, mY);


        toast.show();

    }

  /*  public static void showToast(String text) {

        if (toast == null) {
            toast = Toast.makeText(
                    BaseApplication.getInstance().getContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        int mY = DensityUtil.dip2px(24, BaseApplication.getInstance().getContext());
        toast.setGravity(Gravity.BOTTOM, 0, mY);

        toast.show();
    }*/

   /* public static void showToastCenter(String text) {

//        if (toast == null) {
        toast = Toast.makeText(
                BaseApplication.getInstance().getContext(), text, Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(text);
//            toast.setDuration(Toast.LENGTH_SHORT);
//        }
        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.show();
    }*/

    //自定义Icon+文本居中Toast
    public static Toast showIconAndTextToastCenter(Context context) {
        context = context.getApplicationContext();
        Toast toast = new Toast(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.toast_icon_text, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }


    /**
     * 展示一个自定义view的toast
     *
     * @param text
     * @return
     */
    public static Toast showViewToast(Context context, String text) {
        context = context.getApplicationContext();
        Toast toast = new Toast(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.base_layout_auto_toast, null);

        TextView tv_toast_content = view.findViewById(R.id.tv_toast_content);
        tv_toast_content.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }

}
