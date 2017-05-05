package com.test.emptydemo.cmd;

import android.os.Environment;
import android.util.Log;

import com.test.emptydemo.Utils;

import java.util.Random;

/**
 * @author zhengyx
 * @description 任务
 * @date 2017/5/3
 */
public class Task implements Runnable {
    private long outTime;
    private static final String TAG = "MyTask";

    private String cmd;

    public Task(String cmd) {
        this.cmd = cmd;
    }

    private boolean shutDown = false;

    public void run() {
        try {
//          模拟超时shutdown
            boolean installSilently =
                    Utils.installAndRestart(
                            Environment.getExternalStorageDirectory() + "/superdemo" + "/superdemo.apk");
            Log.d("FileUtil", "installSilently: succeeded-" + installSilently);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " task has been interrupted " + Thread.currentThread().getName());
        }
    }

    /**
     * @description 外部结束任务
     * @author zhengyx
     * @date 2017/5/3
     */
    public void shutDown() {
        shutDown = true;
    }
}
