package com.test.emptydemo;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;

public class BatteryService extends Service {
    private BatteryReceiver receiver = new BatteryReceiver();

    public BatteryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(receiver, ifilter);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
