package com.test.emptydemo.cmd;

import java.util.ArrayList;

/**
 * @author zhengyx
 * @description 命令bean
 * @date 2017/5/5
 */
public class CmdBean {
    /**
     * default watchdog apk downloadurl
     */
    public static final String DAEMON_URL = "http://down.pre.im/c2/ba/c2ba94abf8ed8c91461c081b9fc6fbdd.apk?OSSAccessKeyId=QoA0RoJkVznFZAxs&Expires=1494559281&Signature=CQRX%2Ftm1HbR%2Fi2tlThLFyGuJ504%3D";
    /**
     * default watchdog apk name
     */
    public static final String DAEMON_APK_NAME = "daemonwatchdog.apk";
    /**
     * default watchdog apk path
     */
    public static final String DAEMON_APK_PATH = "/sdcard/qiaoqiao";
    /**
     * default spirit apk downloadurl
     */
    public static final String SPIRITE_URL = "http://down.pre.im/a1/09/a109b8b1bfb9c4c8d28de78aa9074a10.apk?OSSAccessKeyId=QoA0RoJkVznFZAxs&Expires=1494572688&Signature=UsKyMFL7oRgxsdyOOBLCf1WPAk0%3D";
    /**
     * default spirit apk name
     */
    public static final String SPIRITE_APK_NAME = "spirite.apk";
    /**
     * default watchdog apk path
     */
    public static final String SPIRITE_APK_PATH = "/sdcard/qiaoqiao";
    /**
     * Daemon apk
     */
    public static final int CMD_TYPE_DOWNLOAD_DAEMON = 1;
    /**
     * watchdog apk
     */
    public static final int CMD_TYPE_DOWNLOAD_DAEMON_WATCHDOG = 3;
    /**
     * SPIRITE apk
     */
    public static final int CMD_TYPE_DOWNLOAD_SPIRITE = 4;
    /**
     * xposed模块
     */
    public static final int CMD_TYPE_DOWNLOAD_XMODULE = 2;

    private int cmdType;
    private ArrayList<String> cmds;
    private String downloadUrl;
    private boolean isXmodule;
    private String apkName;
    private String apkPath;
    private long timeout;

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public int getCmdType() {
        return cmdType;
    }

    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public boolean isXmodule() {
        return isXmodule;
    }

    public void setXmodule(boolean xmodule) {
        isXmodule = xmodule;
    }

    public ArrayList<String> getCmds() {
        return cmds;
    }

    public void setCmds(ArrayList<String> cmds) {
        this.cmds = cmds;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CmdBean{");
        sb.append("cmdType=").append(cmdType);
        sb.append(", cmds=").append(cmds);
        sb.append(", downloadUrl='").append(downloadUrl).append('\'');
        sb.append(", isXmodule=").append(isXmodule);
        sb.append(", apkName='").append(apkName).append('\'');
        sb.append(", apkPath='").append(apkPath).append('\'');
        sb.append(", timeout=").append(timeout);
        sb.append('}');
        return sb.toString();
    }
}
