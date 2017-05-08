package com.test.emptydemo.cmd;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author zhengyx
 * @description 命令bean
 * @date 2017/5/5
 */
public class CmdBean {
    /**Daemon apk*/
    public static final int CMD_TYPE_DOWNLOAD_DAEMON = 1;
    /**xposed模块*/
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




}
