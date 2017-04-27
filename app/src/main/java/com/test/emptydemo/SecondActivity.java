package com.test.emptydemo;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhengyx
 * @description 注册lable为1test/test
 * @date 2017/2/8
 */
public class SecondActivity extends Activity implements View.OnClickListener {
    Fragment fragment;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        tv.setText("try to click me");
        Utils.showDotMenu(this);
//        android.app.AlertDialog


    }

    @OnClick(R.id.tv)
    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.tv:


                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int groupId = 1;
        int itemId = 1;
        int order = 1;
        String title = "install ";
        menu.add(groupId, itemId, order, title);
        title = "uninstall ";
        menu.add(groupId, itemId + 1, order + 1, title);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "clickitem_" + item.getItemId(), Toast.LENGTH_SHORT).show();
        int itemId = item.getItemId();
        switch (itemId) {
            case 1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.installSilently(Environment.getExternalStorageDirectory() + "/yyb.apk");
                    }
                }).start();

                break;
            case 2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.uninstallSilently("com.tencent.android.qqdownloader");
                    }
                }).start();

                break;


        }
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        Toast.makeText(this, "onMenuItemSelected_" + item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onMenuItemSelected(featureId, item);
    }
}
