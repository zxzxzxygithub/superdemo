package com.test.emptydemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public  class CircleView extends View {

        private Paint paint;

        public CircleView(Context context) {
            super(context);
            initPaint();
        }

        private void initPaint() {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.parseColor("#ff0000"));
            paint.setStyle(Paint.Style.FILL);
            
        }

        public CircleView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initPaint();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float cx=100;
            float cy=100;
            float radius=100;
            canvas.drawCircle(cx,cy,radius,paint);
        }
    }