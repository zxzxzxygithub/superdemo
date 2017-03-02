package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class AddViewBeforeOncreate extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ModelView view = new ModelView(this);
        addContentView(view,new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        view.setBigPicDrawableId(R.drawable.beauty1);
        view.startAni();
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view_before_oncreate);
    }
}
