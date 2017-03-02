package com.test.emptydemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhengyongxiang on 2017/3/1.
 */

public class WelcomAniManagerImpl implements WelcomeAniManager {


    private final Context context;
    private int heightPixels;
    private Animator.AnimatorListener mListener;

    public WelcomAniManagerImpl(Context context) {
        heightPixels = context.getResources().getDisplayMetrics().heightPixels / 2;
        this.context = context;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getXScalePicAni(View view) {
        ObjectAnimator xScaleAnimation = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.3f);
        xScaleAnimation.setInterpolator(new LinearInterpolator());
        return xScaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getYScalePicAni(View view) {
        ObjectAnimator yScaleAnimation = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.3f);
        yScaleAnimation.setInterpolator(new LinearInterpolator());
        return yScaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getXScaleInPicAni(View view) {
        ObjectAnimator xScaleAnimation = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        xScaleAnimation.setInterpolator(new LinearInterpolator());
        return xScaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getYScaleInPicAni(View view) {
        ObjectAnimator yScaleAnimation = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        yScaleAnimation.setInterpolator(new LinearInterpolator());
        return yScaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Animator getIconBottomRaiseCenterAni(View view) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", 0, -heightPixels);
        translationY.setInterpolator(new LinearInterpolator());
        return translationY;
    }

    @Override
    public Animator getCircleRevealAni(View v) {
        // 获取FloatingActionButton的中心点的坐标
        int centerX = (v.getLeft() + v.getRight()) / 2;
        int centerY = (v.getTop() + v.getBottom()) / 2;
        // Math.hypot(x,y):  返回sqrt(x2 +y2)
        // 获取扩散的半径
        float finalRadius = (float) Math.hypot((double) centerX, (double) centerY);
        // 定义揭露动画
        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, 0, finalRadius);
        // 设置动画持续时间，并开始动画
        mCircularReveal.setDuration(4000).start();
        return null;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void showWelcomAni(View scaleBig, View centerIconView, final View zoomIn, View mCircle) {
        Animator xScalePicAni = getXScalePicAni(scaleBig);
        Animator yScalePicAni = getYScalePicAni(scaleBig);
        Animator iconBottomRaiseCenterAni = getIconBottomRaiseCenterAni(centerIconView);
        Animator xScaleInPicAni = getXScaleInPicAni(zoomIn);
        Animator yScaleInPicAni = getYScaleInPicAni(zoomIn);
//        Animator circleRevealAni = getCircleRevealAni(mCircle);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);

        animatorSet.play(xScalePicAni).with(yScalePicAni);
        animatorSet.play(iconBottomRaiseCenterAni).after(xScalePicAni);
//        animatorSet.play(circleRevealAni).after(iconBottomRaiseCenterAni);
        animatorSet.play(xScaleInPicAni).after(iconBottomRaiseCenterAni);
        animatorSet.play(xScaleInPicAni).with(yScaleInPicAni);
        if (mListener == null) {
            Animator.AnimatorListener listener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    zoomIn.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };
            animatorSet.addListener(listener);
        } else {
            animatorSet.addListener(mListener);
        }

        animatorSet.start();
    }

    @Override
    public void setListener(Animator.AnimatorListener listener) {
        this.mListener = listener;
    }
}
