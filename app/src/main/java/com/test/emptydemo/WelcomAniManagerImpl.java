package com.test.emptydemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

/**
 * Created by zhengyongxiang on 2017/3/1.
 */

public class WelcomAniManagerImpl implements WelcomeAniManager {


    private final Context context;
    private int heightPixels;

    public WelcomAniManagerImpl(Context context) {
        heightPixels = context.getResources().getDisplayMetrics().heightPixels / 2;
        this.context = context;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getXScalePicAni(View view) {
        ObjectAnimator xScaleAnimation = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.3f);

        return xScaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getYScalePicAni(View view) {
        ObjectAnimator yScaleAnimation = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.3f);
        return yScaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getXScaleInPicAni(View view) {
        ObjectAnimator xScaleAnimation = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);

        return xScaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getYScaleInPicAni(View view) {
        ObjectAnimator yScaleAnimation = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        return yScaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getIconBottomRaiseCenterAni(View view) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", 0, -heightPixels);
        return translationY;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void showWelcomAni(View scaleBig, View centerIconView, View zoomIn) {
        Animator xScalePicAni = getXScalePicAni(scaleBig);
        Animator yScalePicAni = getYScalePicAni(scaleBig);
        Animator iconBottomRaiseCenterAni = getIconBottomRaiseCenterAni(centerIconView);
        Animator xScaleInPicAni = getXScaleInPicAni(zoomIn);
        Animator yScaleInPicAni = getYScaleInPicAni(zoomIn);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.play(xScalePicAni).with(yScalePicAni);
        animatorSet.play(iconBottomRaiseCenterAni).after(xScalePicAni);
        animatorSet.play(xScaleInPicAni).after(iconBottomRaiseCenterAni);
        animatorSet.play(xScaleInPicAni).with(yScaleInPicAni);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((Activity) context).finish();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }
}
