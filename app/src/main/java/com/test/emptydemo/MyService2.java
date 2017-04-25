package com.test.emptydemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

public class MyService2 extends Service {

    public static final int WHAT_SERVER = 1;
    public static final int WHAT_CLIENT = 2;
    public static final String TAG = "myservice2";

    private Messenger serverMessenger = new Messenger(new ServiceHandler());
    private Messenger clientMessenger = null;


    public MyService2() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return serverMessenger.getBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        clientMessenger = null;
        Log.d(TAG, "unbindService: ");
    }

    private class ServiceHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_SERVER) {

                Bundle bundle = msg.getData();
                String cmd = bundle.getString("cmd");
                if (!TextUtils.isEmpty(cmd)) {
                    Log.d(TAG, "receive from client cmd : " + cmd);
                }

                clientMessenger = msg.replyTo;
                if (clientMessenger != null) {
                    Message message = Message.obtain();
                    message.what = WHAT_CLIENT;
                    Bundle clientBundle = new Bundle();
                    clientBundle.putInt("result", 1);
                    message.setData(clientBundle);
                    try {
                        clientMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.d(TAG, "exception: " + e);
                    }
                }


            }
        }
    }
}
