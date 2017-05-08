package com.test.emptydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "Myreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);
        if (Intent.ACTION_MY_PACKAGE_REPLACED.equals(action) || Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Log.d(TAG, "replace succeeded:packageName " + packageName);
            if ("com.qq.daemonwatchdog".equals(packageName)) {
                Utils.startDeamonService();
            }
        } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            context.startService(new Intent(context, QiqoQiaoDaemonService.class));
        }
    }
}
