package com.test.emptydemo.cmd;

import android.util.Log;

import java.util.Random;

/**
 * @author zhengyx
 * @description 任务
 * @date 2017/5/3
 */
public class Task implements Runnable {
    private long outTime;
    private static final String TAG = "MyTask";
    //统计每个线程总共执行了多少个任务
    private static ThreadLocal<Integer> dealTaskCount = new ThreadLocal<>();

    private String cmd;

    public Task(String cmd) {
        this.cmd = cmd;
    }

    private boolean shutDown = false;

    public void run() {
        if (dealTaskCount==null){
            dealTaskCount=new ThreadLocal<>();
        }
        Integer taskCount = dealTaskCount.get();
        taskCount = taskCount == null ? 1 : ++taskCount;
        dealTaskCount.set(taskCount);

        Log.d(TAG, Thread.currentThread().getName() + " dealing task: " + cmd + ", complete task:" + taskCount);
        try {
            int nextInt = new Random().nextInt(10000);
            Log.d(TAG, "run: randonint " + nextInt);
//          模拟超时shutdown
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, " task has been interrupted " + Thread.currentThread().getName());
            dealTaskCount.remove();
            dealTaskCount = null;
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
