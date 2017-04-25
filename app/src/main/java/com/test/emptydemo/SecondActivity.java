package com.test.emptydemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
    public static final int WHAT_SERVER = 1;
    public static final int WHAT_CLIENT = 2;

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
            if (msg.what == WHAT_CLIENT) {
                Bundle bundle = msg.getData();
                int result = bundle.getInt("result");
                Log.d(TAG, "client receive server handleMessage: result_" + result);
                unbindBrotherService();
            }
        }
    }

    final private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "client connected server: ");
            isBound = true;
            serverMessenger = new Messenger(service);
            Message message = Message.obtain();
            message.replyTo = clientMessenger;
            message.what = WHAT_SERVER;
            Bundle serverBundle = new Bundle();
            serverBundle.putString("cmd", "su ccc");
            message.setData(serverBundle);
            try {
                serverMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.d(TAG, "client onServiceConnected: e_" + e);
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            serverMessenger = null;
            Log.d(TAG, "client onServiceDisconnected: ");
        }
    };

    private void bindBrotherService() {
        if (!isBound) {
            Intent serviceIntent = new Intent("com.test.remoteserviceapp.service2");

            PackageManager pm = getPackageManager();
            //我们先通过一个隐式的Intent获取可能会被启动的Service的信息
            ResolveInfo info = pm.resolveService(serviceIntent, 0);

            if(info != null){
                //如果ResolveInfo不为空，说明我们能通过上面隐式的Intent找到对应的Service
                //我们可以获取将要启动的Service的package信息以及类型
                String packageName = info.serviceInfo.packageName;
                String serviceNmae = info.serviceInfo.name;
                //然后我们需要将根据得到的Service的包名和类名，构建一个ComponentName
                //从而设置intent要启动的具体的组件信息，这样intent就从隐式变成了一个显式的intent
                //之所以大费周折将其从隐式转换为显式intent，是因为从Android 5.0 Lollipop开始，
                //Android不再支持通过通过隐式的intent启动Service，只能通过显式intent的方式启动Service
                //在Android 5.0 Lollipop之前的版本倒是可以通过隐式intent启动Service
                ComponentName componentName = new ComponentName(packageName, serviceNmae);
                serviceIntent.setComponent(componentName);
                try{
                    bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
                    Log.d(TAG, "client bindBrotherService: ");
                }catch(Exception e){
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }

        }

    }

    private void unbindBrotherService() {
        if (isBound) {
            unbindService(serviceConnection);
            Log.d(TAG, "client unbindBrotherService: ");
            isBound = false;

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
        unbindBrotherService();
    }
}


