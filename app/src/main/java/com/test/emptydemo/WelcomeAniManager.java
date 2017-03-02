package com.test.emptydemo;

import android.animation.Animator;
import android.view.View;

/**
 * Created by zhengyongxiang on 2017/3/1.
 */

public interface WelcomeAniManager {

    Animator getXScalePicAni(View view);

    Animator getYScalePicAni(View view);

    Animator getXScaleInPicAni(View view);

    Animator getYScaleInPicAni(View view);

    Animator getIconBottomRaiseCenterAni(View view);

    Animator getCircleRevealAni(View view);

    void showWelcomAni(View scaleBig, View up, View zoomIn, View mCircle);

    void setListener(Animator.AnimatorListener listener);
}
