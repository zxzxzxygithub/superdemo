package com.test.emptydemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class SuperService extends Service {
    private static final String TAG = "superservice";

    public SuperService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String wxa = intent.getStringExtra("wxa");
            FileUtil.saveContentToSdcard("WxAddress" + System.currentTimeMillis() +
                    ".txt", wxa);
            Log.d(TAG, "onStartCommand: save wxaddress to sdcard");
        }


        return super.onStartCommand(intent, flags, startId);
    }
}
