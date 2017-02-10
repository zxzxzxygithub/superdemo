package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;

import com.test.emptydemo.lib.LargeImageView;

import java.io.IOException;

public class LargeImgActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_img);
        LargeImageView lv= (LargeImageView) findViewById(R.id.lv);
        try {
            lv.setInputStream(getAssets().open("qm.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
