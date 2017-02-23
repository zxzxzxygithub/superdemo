package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @author zhengyx
 * @description 注册lable为1test/test
 * @date 2017/2/8
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        OutScreenHeightView viewById = (OutScreenHeightView) findViewById(R.id.outscreenview);
    }
}
