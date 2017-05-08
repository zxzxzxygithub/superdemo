package com.test.emptydemo.cmd;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.test.emptydemo.Utils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhengyx
 * @description 任务
 * @date 2017/5/3
 */
public class Task implements Runnable {
    private  ThreadPool threadPool;
    private long outTime=1000;
    private static final String TAG = "MyTask";

    private ArrayList<String> cmds;

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Logger.d("outoftime  threadPool.removeTask");
            threadPool.removeTask(Task.this);
        }
    };
    private Timer timer;

    public Task(ThreadPool threadPool,ArrayList<String> cmds) {
        this.threadPool = threadPool;
        this.cmds = cmds;
        timer = new Timer();
    }

    public void run() {
        if (outTime != 0) {
            timer.schedule(timerTask, outTime);
            Logger.d(" timer.schedule(timerTask--outtime--" + outTime);
        }
        try {
//            Utils.execShellCmds(cmds);
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " task has been interrupted " + Thread.currentThread().getName());
            timer.cancel();
            Logger.d("interrupted timer cancel");
        }
        timer.cancel();
        Logger.d("finish run timer cancel");
    }

}
