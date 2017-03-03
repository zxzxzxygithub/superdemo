package com.test.emptydemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhengyx
 * @description 注册lable为1test/test
 * @date 2017/2/8
 */
public class SecondActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        tv.setText("try to write http://siliao.com/app/  or  http://siliao.com/detail to qq\n 在qq选择用浏览器打开你的这个链接就会弹出对话框调用你的应用");
    }

    @OnClick(R.id.tv)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv:
                Toast.makeText(this, "hello world", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
