package com.test.emptydemo;

import android.animation.Animator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Toast;

/**
 * @author zhengyx
 * @description 注册lable为1test/test
 * @date 2017/2/8
 */
public class SecondActivity extends Activity {

    private CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    public  void circlereveal(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int centerX = circleView.getWidth() / 2;
            int centerY = -circleView.getHeight() / 2;
            int cicular_R = circleView.getWidth() / 2 ;
            Animator animator = ViewAnimationUtils.createCircularReveal(circleView, centerX, centerY, 0, cicular_R);
            animator.setDuration(1000);
            animator.start();
        } else {
            Toast.makeText(this, "SDK版本太低,请升级", Toast.LENGTH_SHORT).show();
        }

    }


}
