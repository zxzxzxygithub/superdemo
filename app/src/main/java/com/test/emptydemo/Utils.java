package com.test.emptydemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewConfiguration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by zhengyongxiang on 2017/4/20.
 */

public class Utils {

    private static final String TAG = "superutils";
    private static final String CMD_CHMOD777 = "chmod 777 ";
    private static final String CMD_REBOOT = " reboot  ";
    private static String CMD_INSTALL = "pm install -r ";
    private static String CMD_UNINSTALL = "pm uninstall ";

    /**
     * @description 标题栏显示点点点下拉菜单
     * @author zhengyx
     * @date 2017/4/20
     */
    public static void showDotMenu(Context context) {
        try {
            ViewConfiguration mconfig = ViewConfiguration.get(context);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(mconfig, false);
            }
        } catch (Exception ex) {
            XposedBridge.log(ex);
        }

    }

    /**
     * @description 标题栏显示点点点下拉菜单
     * @author zhengyx
     * @date 2017/4/20
     */
    public static void showDotMenuInFragment() {
        Class<Fragment> fragmentClass = Fragment.class;
        try {
            Fragment fragment = fragmentClass.newInstance();
            Method hasOptionsMenu = fragmentClass.getMethod("setHasOptionsMenu", boolean.class);
            hasOptionsMenu.invoke(fragment, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     *
     * @param pkgName 要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public static boolean uninstallSilently(String pkgName) {
        // 执行pm install命令
        String command = CMD_UNINSTALL + pkgName + "\n";
        boolean result = execShellCmd(command);
        return result;
    }

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     *
     * @param apkPath 要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public static boolean installSilently(String apkPath) {
        // 执行pm install命令
        String command = CMD_INSTALL + apkPath + "\n";
        boolean result = execShellCmd(command);
        return result;
    }

    /**
     * @description 设置文件的777权限
     * @author zhengyx
     * @date 2017/5/3
     */
    public static boolean set777Permission(String filePath) {
        // 执行CMD_CHMOD777命令
        String command = CMD_CHMOD777 + filePath + "\n";
        boolean result = execShellCmd(command);
        Log.d(TAG, "set777Permission: "+result);
        return result;
    }

    /**
     * @description 设置文件的777权限
     * @author zhengyx
     * @date 2017/5/3
     */
    public static boolean rebootPhone() {
        // 执行 CMD_REBOOT
        String command = CMD_REBOOT+"\n";
        boolean result = execShellCmd(command);
        Log.d(TAG, "rebootPhone: "+result);
        return result;
    }

    /**
     * @description 执行shell命令
     * @author zhengyx
     * @date 2017/4/27
     */
    private static boolean execShellCmd(String command) {
        Log.d(TAG, "shellCmd start " + command);
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Log.d(TAG, "result msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
                Log.d(TAG, command + " shellCmd succeeded ");
            } else {
                Log.d(TAG, command + " shellCmd failed ");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            Log.d(TAG, command + " shellCmd failed ");
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return result;
    }


    /**
     * @description 直接通过文件写入的方式操作其他app的sp文件
     * @author zhengyx
     * @date 2017/5/3
     */
    public static  void writeOtherAppSpWithFileWriting() {
        Utils.set777Permission("data/data/de.robv.android.xposed.installer/shared_prefs/enabled_modules.xml");
        String pkg = "com.test.emptydemo";
        File file = new File("data/data/de.robv.android.xposed.installer/shared_prefs", "enabled_modules.xml");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String readLine;
            while ((readLine=bufferedReader.readLine())!= null) {
                String mapStr = "</map>";
                String mapStr2="<map />";
                if (readLine.contains(mapStr)&&!stringBuilder.toString().contains(pkg)) {
                    stringBuilder.append("<int name=\"" +
                            pkg +
                            "\" value=\"1\" /> \n");
                    stringBuilder.append(mapStr);
                } else if(readLine.contains(mapStr2)&&!stringBuilder.toString().contains(pkg)){
                    stringBuilder.append("<map> \n");
                    stringBuilder.append("<int name=\"" +
                            pkg +
                            "\" value=\"1\" /> \n");
                    stringBuilder.append(mapStr);
                }else{
                    stringBuilder.append(readLine+"\n");
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(stringBuilder.toString().getBytes());
            Utils.rebootPhone();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
