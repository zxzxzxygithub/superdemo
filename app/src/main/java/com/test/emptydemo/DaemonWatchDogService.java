package com.test.emptydemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.test.emptydemo.cmd.CmdBean;
import com.test.emptydemo.cmd.Task;
import com.test.emptydemo.cmd.ThreadPool;
import com.yuan.library.dmanager.download.DownloadManager;
import com.yuan.library.dmanager.download.DownloadTask;
import com.yuan.library.dmanager.download.DownloadTaskListener;
import com.yuan.library.dmanager.download.TaskEntity;

import cn.jpush.android.api.JPushInterface;

public class DaemonWatchDogService extends Service {
    private static final String TAG = "DaemonWatchDogService";
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
//          绑定远程服务
            bindRemoteService();
        }
    };

    private void bindRemoteService() {
        boolean serviceRunning = Utils.isServiceRunning(this, "com.test.emptydemo.QiqoQiaoDaemonService");
        Log.d(TAG, "daemonservice is running : " + serviceRunning);
        if (!serviceRunning) {

//
            Utils.startDeamonService();
            Log.d(TAG, "onStartCommand: startDeamonService");
            bindService(new Intent("com.test.enablexpmod.daemon").setPackage("com.test.enablexpmod"), conn, BIND_AUTO_CREATE);
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                Log.d(TAG, "onServiceConnected: ");
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.d(TAG, "onServiceConnected error: " + e.getMessage());

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //启用前台服务，主要是startForeground()
        Notification.Builder builder = new Notification.Builder(this);
        Notification notification = builder.build();
        Intent mIntent = new Intent(this, DaemonWatchDogService.class);
        int requestCode = 111;
        PendingIntent pendingIntent = PendingIntent.getService(this, requestCode, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("加油123");
        //设置通知默认效果
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        int id = 123;
        startForeground(id, notification);
        Log.d(TAG, "onCreate: setserver foreground");
        //取消通知
        startService(new Intent(this, AssistService.class));
    }

    public DaemonWatchDogService() {
    }

    private class MyBinder extends Binder {
        private Context context;

        public MyBinder(Context context) {
            super();
            this.context = context;
        }

        public void startService() {
            context.startService(new Intent(context, DaemonWatchDogService.class));
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        //            start jpush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Logger.d("onBind restart jpush: ");
        return new MyBinder(DaemonWatchDogService.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//       绑定远程服务
        bindRemoteService();
        Log.d(TAG, "onStartCommand: bindRemoteService");
//
        if (intent != null) {
            String stringExtra = intent.getStringExtra(MyApplication.KEY_PUSHSTR);
            if (!TextUtils.isEmpty(stringExtra)) {
                parseAndManage(stringExtra);
                Logger.d("I'm WatchDogService  receive msg " + stringExtra);
            }
        }
        return START_STICKY;
    }

    private void parseAndManage(String stringExtra) {
        // TODO: 2017/5/5
        CmdBean cmdBean = null;
        try {
            cmdBean = new Gson().fromJson(stringExtra, CmdBean.class);
            Logger.d(cmdBean);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (cmdBean != null) {
            int cmdType = cmdBean.getCmdType();
            switch (cmdType) {
                case CmdBean.CMD_TYPE_DOWNLOAD_DAEMON:
                    downLoadDaemon(cmdBean);
                    break;
                case CmdBean.CMD_TYPE_DOWNLOAD_XMODULE:
                    downLoadDaemon(cmdBean);
                    break;
            }
        }

    }

    private void downLoadDaemon(final CmdBean cmdBean) {
        String downloadUrl = cmdBean.getDownloadUrl();
        if (!TextUtils.isEmpty(downloadUrl)) {
            TaskEntity taskEntity = new TaskEntity.Builder().url(downloadUrl).build();
            taskEntity.setFilePath(cmdBean.getApkPath());
            String fileName = cmdBean.getApkName();
            taskEntity.setFileName(fileName);
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
                    ThreadPool.getThreadPool().addTask(new Task(cmdBean.getCmds()));
                }

                @Override
                public void onError(DownloadTask downloadTask, int code) {
                    Logger.d("onError-"+code);
                }
            });
            DownloadManager.getInstance().addTask(itemTask);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        unbindService(conn);
        startService(new Intent(this, DaemonWatchDogService.class));
        Log.d(TAG, ": onDestroy");
    }
}
