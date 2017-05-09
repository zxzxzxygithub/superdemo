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
    private String pkg;
    private ThreadPool threadPool;
    private long outTime;
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
    private boolean isXmodule;

    public Task(ThreadPool threadPool, ArrayList<String> cmds) {
        this.threadPool = threadPool;
        this.cmds = cmds;
        timer = new Timer();
    }

    public Task(ThreadPool threadPool, CmdBean cmdBean) {
        this.threadPool = threadPool;
        this.cmds = cmdBean.getCmds();
        isXmodule = cmdBean.isXmodule();
        timer = new Timer();
        pkg = cmdBean.getPkgName();
    }

    public void run() {
        if (outTime != 0) {
            timer.schedule(timerTask, outTime);
            Logger.d(" timer.schedule(timerTask--outtime--" + outTime);
        }
        try {
            Utils.execShellCmds(cmds);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " task has been interrupted " + Thread.currentThread().getName());
            timer.cancel();
            Logger.d("interrupted timer cancel");
        }
        if (isXmodule) {
            Utils.writeOtherAppSpWithFileWriting(pkg);
            Logger.d("isXmodule activate xmodule and restart ");
        }
        timer.cancel();
        Logger.d("finish run timer cancel");
    }

}
