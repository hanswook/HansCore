package com.mckj.tec_library.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.mckj.tec_library.R;
import com.mckj.tec_library.utils.EmptyUtils;

public class LoadingDialogManager {
    private Activity activity;
    public AlertDialog dialog = null;

    public LoadingDialogManager(Activity activity) {
        this.activity = EmptyUtils.checkNotNull(activity);
    }
    public void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialog);
        LayoutInflater inflater = null;
        if (activity != null) {
            inflater = activity.getLayoutInflater();
        } else {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        final Animation operatingAnim = AnimationUtils.loadAnimation(activity, R.anim.base_anim_tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        if (dialog == null) {
            //获取自定义布局
            final View layout = inflater.inflate(R.layout.base_loading_dialog, null);
            builder.setView(layout);
            ImageView loadingImg = (ImageView) layout.findViewById(R.id.loading_img);
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            loadingImg.startAnimation(operatingAnim);
        }else {
            ImageView loadingImg2 = (ImageView) dialog.findViewById(R.id.loading_img);
            loadingImg2.startAnimation(operatingAnim);
        }

        dialog.show();
    }
    public void dismissLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
