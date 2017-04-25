package com.test.emptydemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zhengyongxiang on 2017/4/25.
 */

public class Utils {

    public static final String TAG = "testservicerun";

    public static void sendService(Context context, String processName) {

        boolean find = false;
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Intent serviceIntent = new Intent();
        String packageName = context.getPackageName();
        for (ActivityManager.RunningServiceInfo runningServiceInfo : mActivityManager.getRunningServices(100)) {
            if (runningServiceInfo.process.contains(processName)
                    && runningServiceInfo.service.getPackageName().equals(packageName)) {//判断service是否在运行
                Log.e(TAG, "process:" + runningServiceInfo.process);
                find = true;
            }
        }
        //判断服务是否起来，如果服务没起来，就唤醒
        if (!find) {
            serviceIntent.setPackage(packageName);
            serviceIntent.setAction(packageName + "." + processName);
            context.startService(serviceIntent);
            Toast.makeText(context, "开始唤醒 Servcie " + processName, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, processName + "_ 不用唤醒 ", Toast.LENGTH_SHORT).show();
        }
    }


}
