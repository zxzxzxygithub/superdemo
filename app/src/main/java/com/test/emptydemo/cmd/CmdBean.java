package com.test.emptydemo.cmd;

/**
 * @author zhengyx
 * @description 命令bean
 * @date 2017/5/5
 */
public class CmdBean {

    public static final int CMD_TYPE_DOWNLOAD_DAEMON = 1;
    private int cmdType;
    private String cmd;
    private String downloadUrl;
    private boolean isXmodule;

    public int getCmdType() {
        return cmdType;
    }

    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
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
}
