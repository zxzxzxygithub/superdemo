package com.test.emptydemo;

import android.app.Application;
import android.content.Context;

import com.yuan.library.dmanager.download.DownloadManager;

import cn.jpush.android.api.JPushInterface;

/**
 * @author zhengyx
 * @description application类，初始化jpush
 * @date 2017/4/13
 */
public class MyApplication extends Application {
    public static final String KEY_PUSHSTR = "key_pushstr";
    public static final String ACTION_PUSH = "com.example.zhengyx.input";
    public static final int REQUEST_CODE_LOCKSCREE = 9999;
    public static final String KEY_LOCKSCREEN = "lockscreen";
    public static final String ACTION_FINISH = "com.zhengyx.action.finish";
    public static final String KEY_DOORDER = "key_order";
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        DownloadManager.getInstance().init(this, 9);
        CrashHandler mCrashHandler = CrashHandler.getInstance();
        mCrashHandler.setCrashHandlerInfo(this);
    }


}
