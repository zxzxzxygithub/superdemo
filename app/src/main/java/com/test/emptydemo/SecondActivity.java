package com.test.emptydemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

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
    }

    @OnClick(R.id.tv)
    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.tv:
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String deviceId = telephonyManager.getDeviceId();
                Toast.makeText(this, "deviceiD_" + deviceId, Toast.LENGTH_SHORT).show();
                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int groupId = 1;
        int itemId = 1;
        int order = 1;
        String title = "this is an addmenu";
        menu.add(groupId, itemId, order, title);
        menu.add("menu 2");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "clickitem_" + item.getItemId(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        Toast.makeText(this, "onMenuItemSelected_" + item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onMenuItemSelected(featureId, item);
    }
}
