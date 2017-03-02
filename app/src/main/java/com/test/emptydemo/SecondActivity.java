package com.test.emptydemo;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhengyx
 * @description 注册lable为1test/test
 * @date 2017/2/8
 */
public class SecondActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.modelview)
    ModelView modelview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        tv.setText("try to click me");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WelcomeAniManager welcomAniManager = new WelcomAniManagerImpl(SecondActivity.this);
                welcomAniManager.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        clearFullScreenFlag();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                modelview.setWelcomManager(welcomAniManager);
                modelview.startAni();
            }
        }, 1000);
    }

    @OnClick(R.id.tv)
    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.tv:
                Toast.makeText(this, "hello world", Toast.LENGTH_SHORT).show();
                break;
        }


    }

    public void clearFullScreenFlag() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
//取消全屏设置
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
