package com.test.emptydemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.test.emptydemo.cmd.MyConstants;

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
        tv.setText("play script");
        Utils.showDotMenu(this);
//        android.app.AlertDialog


    }

    int itemId = 2;

    @OnClick(R.id.tv)
    @Override
    public void onClick(View v) {


        int id = v.getId();
        switch (id) {
            case R.id.tv:
                if (itemId == 1) {
                    itemId = 2;
                } else if (itemId == 2) {
                    itemId = 1;
                }
                Intent serviceIntent = new Intent();
                serviceIntent.setComponent(new ComponentName("net.aisence.Touchelper", "net.aisence.Touchelper.TouchelperService"));
//                serviceIntent.putExtra("intent_timer",0);//默认为0
                serviceIntent.putExtra("intent_timer", 0l);
//                serviceIntent.putExtra("intent_file", "/sdcard/touchelf/scripts/stringtest.lua");
                serviceIntent.putExtra("intent_file", "/sdcard/touchelf/scripts/yyy.lua");
                serviceIntent.putExtra("intent_mode", 2);
                serviceIntent.putExtra("intent_play", 1);
                serviceIntent.putExtra("intent_DEBUGGER", "");
                serviceIntent.putExtra("intent_UI", "");
                switch (itemId) {
                    case 1:
                        serviceIntent.putExtra("cmd", MyConstants.CMD_RUN);
                        Logger.d("run cmd_run");
                        break;

                    case 2:
                        serviceIntent.putExtra("cmd", MyConstants.CMD_STOP);
                        Logger.d("run cmd_stop");
                        break;

                }
                if (itemId != 3) {
                    startService(serviceIntent);
                }

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

                    }
                }).start();

                break;
            case 2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.uninstallSilently("ccom.test.emptydemo");
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
