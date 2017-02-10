package com.test.emptydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ObscuredActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obscured);
        ImageView iv_obscured = (ImageView) findViewById(R.id.iv_obscured);
//        ratio越大越节省内存，越模糊
        int scaleRatio = 50;
        int blurRadius = 8;
        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.testobscure);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                originBitmap.getWidth() / scaleRatio,
                originBitmap.getHeight() / scaleRatio,
                false);
        Bitmap blurBitmap = FastBlurUtil.doBlur(scaledBitmap, blurRadius, true);
        iv_obscured.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv_obscured.setImageBitmap(blurBitmap);
    }
}
