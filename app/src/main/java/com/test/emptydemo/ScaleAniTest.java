package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScaleAniTest extends Activity implements View.OnClickListener {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.activity_scale_ani_test)
    RelativeLayout activityScaleAniTest;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_ani_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    @Override
    public void onClick(View v) {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scaleout);
        textView.startAnimation(animation);

    }
}
