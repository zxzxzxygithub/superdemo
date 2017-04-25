package com.test.emptydemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhengyx
 * @description 注册lable为1test/test
 * @date 2017/2/8
 */
public class SecondActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "SecondActivity";

    private boolean isBound = false;

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        tv.setText("try to click me");
    }

    @OnClick(R.id.tv)
    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.tv:
                bindBrotherService();
                break;
        }


    }

    private Messenger clientMessenger = new Messenger(new ClientHandler());
    private Messenger serverMessenger = null;

    private class ClientHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MyService2.WHAT_CLIENT) {
                Bundle bundle = msg.getData();
                int result = bundle.getInt("result");
                Log.d(TAG, "handleMessage: result_" + result);

            }
        }
    }

    final private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            isBound = true;
            serverMessenger = new Messenger(service);
            Message message = Message.obtain();
            message.replyTo = clientMessenger;
            message.what = MyService2.WHAT_SERVER;
            Bundle serverBundle = new Bundle();
            serverBundle.putString("cmd", "su ccc");
            message.setData(serverBundle);
            try {
                serverMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.d(TAG, "onServiceConnected: e_" + e);
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            serverMessenger = null;
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    private void bindBrotherService() {
        if (!isBound) {

            Intent serviceIntent = new Intent(this, MyService2.class);
            bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
            Log.d(TAG, "bindBrotherService: ");
        }

    }

    private void unbindBrotherService() {
        if (isBound) {
            unbindService(serviceConnection);
            Log.d(TAG, "unbindBrotherService: ");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "bind");
        menu.add(1, 2, 2, "unbind");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                bindBrotherService();
                break;
            case 2:
                unbindBrotherService();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clientMessenger = null;
    }
}


