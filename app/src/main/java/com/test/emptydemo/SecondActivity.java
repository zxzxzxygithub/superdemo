package com.test.emptydemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
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
//                Bundle EXTRA: Bundle EXTRA 1:Bundle Class: java.lang.Long
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Key: intent_timer
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Value: 0
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle EXTRA 2:Bundle Class: java.lang.String
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Key: intent_file
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Value: /mnt/sdcard/touchelf/scripts/yyy.lua
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle EXTRA 3:Bundle Class: java.lang.Integer
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Key: intent_mode
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Value: 2
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle EXTRA 4:Bundle Class: java.lang.Integer
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Key: intent_play
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Value: 1
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle EXTRA 5:Bundle Class: java.lang.String
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Key: intent_DEBUGGER
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Value:
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle EXTRA 6:Bundle Class: java.lang.String
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Key: intent_UI
//                04-24 15:19:30.554 I/Xposed  (13873):  Bundle Value:
                Intent serviceIntent = new Intent();
                serviceIntent.setComponent(new ComponentName("net.aisence.Touchelper", "net.aisence.Touchelper.TouchelperService"));
//                serviceIntent.putExtra("intent_timer",0);//默认为0
                serviceIntent.putExtra("intent_timer", 0);
                serviceIntent.putExtra("intent_file", "/mnt/sdcard/touchelf/scripts/yyy.lua");
                serviceIntent.putExtra("intent_mode", 2);
                serviceIntent.putExtra("intent_play", 1);
                serviceIntent.putExtra("intent_DEBUGGER", "");
                serviceIntent.putExtra("intent_UI", "");
                startService(serviceIntent);

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
