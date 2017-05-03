package com.test.emptydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

//BATTERY_STATUS_CHARGING需要再代码中注册
public class BatteryReceiver extends BroadcastReceiver {
    private static final String TAG = "batterybroadcast";


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "reveiving battery", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "action: " + intent.getAction());
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        Log.d(TAG, "status: " + status);

        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        Log.d(TAG, "isCharging: " + isCharging);

// 怎么充
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        Log.d(TAG, "usbCharge: " + usbCharge);

        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Log.d(TAG, "acCharge: " + acCharge);


//当前剩余电量

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        Log.d(TAG, "level: " + level);


//电量最大值

        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        Log.d(TAG, "scale: " + scale);


//电量百分比
        float batteryPct = level / (float) scale;
        Log.d(TAG, "batteryPct: " + batteryPct);

    }
}
