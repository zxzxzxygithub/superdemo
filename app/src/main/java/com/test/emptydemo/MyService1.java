package com.test.emptydemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService1 extends Service {
    public static final String TAG = "myservice1";


    final private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    public MyService1() {
    }

    private final IBinder binder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return binder;
    }

    public class MyBinder extends Binder {
        MyService1 getService() {
            return MyService1.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "Service 1111111 已经唤醒", Toast.LENGTH_LONG).show();
        bindBrotherService();
        Log.d(TAG, "onStartCommand: ");
        return START_STICKY;
    }

    private void bindBrotherService() {
        Intent serviceIntent = new Intent(this, MyService2.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "bindBrotherService: ");

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        Log.d(TAG, "unbindService: ");
    }


}
