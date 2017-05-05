package com.test.emptydemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yuan.library.dmanager.download.DownloadManager;
import com.yuan.library.dmanager.download.DownloadTask;
import com.yuan.library.dmanager.download.DownloadTaskListener;
import com.yuan.library.dmanager.download.TaskEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author zhengyx
 * @description 注册lable为1test/test
 * @date 2017/2/8
 */
public class SecondActivity2 extends Activity implements View.OnClickListener {
    Fragment fragment;
    @BindView(R.id.tv)
    TextView tv;
    private Context context;

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
                String url = "http://down.pre.im/59/78/5978b4553ccc6cd95a4dfed95515c8ce.apk?OSSAccessKeyId=QoA0RoJkVznFZAxs&Expires=1494229007&Signature=MD%2FO%2F%2Bf2CC9LJUOLGGY%2FeM6wLds%3D";
                TaskEntity taskEntity = new TaskEntity.Builder().url(url).build();
                taskEntity.setFilePath("/sdcard/aqq/download/apk");
                taskEntity.setFileName("test.apk");
                DownloadTask itemTask = new DownloadTask(taskEntity);
                itemTask.setListener(new DownloadTaskListener() {

                    @Override
                    public void onQueue(DownloadTask downloadTask) {

                        Logger.d("onQueue");
                    }

                    @Override
                    public void onConnecting(DownloadTask downloadTask) {
                        Logger.d("onConnecting");
                    }

                    @Override
                    public void onStart(DownloadTask downloadTask) {
                        Logger.d("onStart");
                    }

                    @Override
                    public void onPause(DownloadTask downloadTask) {
                        Logger.d("onPause");
                    }

                    @Override
                    public void onCancel(DownloadTask downloadTask) {
                        Logger.d("onCancel");
                    }

                    @Override
                    public void onFinish(DownloadTask downloadTask) {
                        Logger.d("onFinish");
                    }

                    @Override
                    public void onError(DownloadTask downloadTask, int code) {
                        Logger.d("onError");
                    }
                });
                DownloadManager.getInstance().addTask(itemTask);

                break;
        }


    }


    /**
     * @description 在其他app的sp中写入值，暂时失败
     * @author zhengyx
     * @date 2017/5/3
     */
    @Deprecated
    private void writeOtherAppSpWithContext() {
        try {
            context = this.createPackageContext("de.robv.android.xposed.installer", Context.CONTEXT_IGNORE_SECURITY);
            SharedPreferences sharedPreferences = context.getSharedPreferences("enabled_modules",
                    MODE_MULTI_PROCESS | MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("com.test.emptydemo", 1).apply();

        } catch (Exception e) {
            e.printStackTrace();
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
