package com.test.emptydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;


/**
 * @author zhengyx
 * @description horizontalverticalconflic
 * @date 2017/2/8
 */
public class SecondActivity extends AppCompatActivity implements View.OnClickListener{


    public static final String TAG="SecondActivity";
    private ScrollView scrollView;
    private HorizontalScrollView innersc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontalandvertical);
        scrollView = (ScrollView) findViewById(R.id.outsc);
        innersc = (HorizontalScrollView) findViewById(R.id.innersc);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                innersc.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        innersc.setOnTouchListener(new View.OnTouchListener() {
            public int lastY;
            public int lastX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                innersc.getParent().requestDisallowInterceptTouchEvent(true);
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaY = y - lastY;
                        int deltaX = x - lastX;
                        if (Math.abs(deltaX) < Math.abs(deltaY)) {
                            innersc.getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            innersc.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    default:
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }




    @Override
    public void onClick(View v) {
    }
}
