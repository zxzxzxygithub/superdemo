package com.test.emptydemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import static android.R.attr.radius;

/**
 * Created by zhengyongxiang on 2017/2/22.
 */

public class OutScreenHeightView extends View {
    public static final String TAG = "OUTSCREENVIEW";
    private int widthPixels;
    private int heightPixels;
    private Paint paint;

    public OutScreenHeightView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        widthPixels = 2 * displayMetrics.widthPixels;
        heightPixels = 2 * displayMetrics.heightPixels;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStyle(Paint.Style.STROKE);

    }

    public OutScreenHeightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthPixels, heightPixels);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: " + left + "_" + top + "_" + right + "_" + bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = 900;
        float cx = 450;
        float cy = 450;
        canvas.drawCircle(cx, cy, radius, paint);

    }
}
