package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class WelcomShowModelPageActivity extends Activity {

    private View mShowPicture;

    private ImageView mCenterIcon;
    private View mRlbg;
    private ImageView mCircle;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelshow);

        mShowPicture = findViewById(R.id.bg);
        mRlbg = findViewById(R.id.rlbg);
        mCenterIcon = (ImageView) findViewById(R.id.centericon);
        mCircle = (ImageView) findViewById(R.id.circle);
        init();
    }

    private void init() {
        initPicture();
        WelcomeAniManager welcomAniManager = new WelcomAniManagerImpl(this);
        welcomAniManager.showWelcomAni(mShowPicture, mCenterIcon, mRlbg,mCircle);
    }


    private void initPicture() {
        mShowPicture.setBackgroundResource(R.drawable.beauty1);
    }

}