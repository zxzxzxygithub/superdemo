package com.test.emptydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author zhengyx
 * @description
 * @date 2017/2/8
 */
public class ThirdActivity extends Activity {

    public static final  String DIALOG="dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView text = (TextView) findViewById(R.id.activity_second);
        text.setText("点我启动service弹出对话框");
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service = new Intent(ThirdActivity.this, MyService.class);
                service.putExtra(DIALOG,true);
                startService(service);
            }
        });
    }
}
