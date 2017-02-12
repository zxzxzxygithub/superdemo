package com.test.emptydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class ObscuredActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obscured);
        final TextView iv_obscured = (TextView) findViewById(R.id.iv_obscured);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable backgroundDrawable = iv_obscured.getBackground();
                BitmapDrawable  bitmapDrawable= (BitmapDrawable) backgroundDrawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
//        ratio越大越节省内存，越模糊
                int blurRadius = 8;
                int scaleRatio =8;
                Bitmap blurBitmap = FastBlurUtil.doBlur(bitmap, scaleRatio, blurRadius);
                final BitmapDrawable bitmapDrawable1=new BitmapDrawable(getResources(),blurBitmap);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_obscured.setBackground(bitmapDrawable1);
                    }
                });

            }
        }).start();


    }
}
