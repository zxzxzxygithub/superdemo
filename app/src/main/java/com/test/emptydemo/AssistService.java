package com.test.emptydemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

/**
 * @author zhengyx
 * @description 用于取消前台服务的通知栏图标
 * @date 2017/5/3
 */
public class AssistService extends Service {
    private static final String TAG = "AssistService";

    public AssistService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //启用前台服务，主要是startForeground()
        Notification.Builder builder = new Notification.Builder(this);
        Notification notification = builder.build();
        Intent mIntent = new Intent(this, AssistService.class);
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
        stopForeground(true);
        Log.d(TAG, "onCreate: cancel foreground note ");
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
