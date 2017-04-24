package com.test.emptydemo.xposed;

import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author zhengyx
 * @description xpose模块处理入口
 * @date 2017/4/18
 */
public class Main implements IXposedHookLoadPackage {
    private static final int GROUP_ID = 11;//hooked menugroupid
    private static final int ITERM_ID = 11;//hooked menugroupid
    private static final String NEWLINE = "\n";

    // idem
    private void hook_method(String className, ClassLoader classLoader, String methodName,
                             Object... parameterTypesAndCallback) {
        try {
            XposedHelpers.findAndHookMethod(className, classLoader, methodName, parameterTypesAndCallback);
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        handleLoadPackage4release(loadPackageParam);

//        final String packageName = "com.test.emptydemo";
//        String filePath = String.format("/data/app/%s-%d/base.apk", packageName, 1);
//        if (!new File(filePath).exists()) {
//            filePath = String.format("/data/app/%s-%d/base.apk", packageName, 2);
//            if (!new File(filePath).exists()) {
//                XposedBridge.log("Error:在/data/app找不到APK文件" + packageName);
//                return;
//            }
//        }
//        try {
//            final PathClassLoader pathClassLoader = new PathClassLoader(filePath, ClassLoader.getSystemClassLoader());
//            final Class<?> aClass = Class.forName("com.test.emptydemo.xposed.Main", true, pathClassLoader);
//            final Method aClassMethod = aClass.getMethod("handleLoadPackage4release", XC_LoadPackage.LoadPackageParam.class);
//            aClassMethod.invoke(aClass.newInstance(), loadPackageParam);
//        } catch (ClassNotFoundException e) {
//            XposedBridge.log(e);
//        } catch (NoSuchMethodException e) {
//            XposedBridge.log(e);
//        } catch (SecurityException e) {
//            XposedBridge.log(e);
//        } catch (IllegalAccessException e) {
//            XposedBridge.log(e);
//        } catch (IllegalArgumentException e) {
//            XposedBridge.log(e);
//        } catch (InvocationTargetException e) {
//            XposedBridge.log(e);
//        } catch (InstantiationException e) {
//            XposedBridge.log(e);
//        }
    }

    public void handleLoadPackage4release(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
// 过滤掉非触摸精灵
        if (!loadPackageParam.packageName.equals("net.aisence.Touchelper")) {
            return;
        }
        try {
//        尝试hook 触摸精灵 start
            tryTohookTouchSpiriteService(loadPackageParam);
//            tryTohookTouchSpiriteDoPlay(loadPackageParam);
//        尝试hook 触摸精灵  end、
            XposedBridge.log("------------5");
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    private void tryTohookTouchSpiriteDoPlay(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        String className = "net.aisence.Touchelper.TouchelperBin";
        ClassLoader classLoader = loadPackageParam.classLoader;
        String methodStr = "doPlay";
        XposedHelpers.findAndHookMethod(className, classLoader, methodStr, Object.class, String.class, String.class, long.class, String.class, String.class, String.class, String.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
//                Object paramObject,  String paramString1, String paramString2,
//                 long paramLong,     String paramString3, String paramString4,
//                String paramString5, String paramString6, boolean paramBoolean

                Object arg = methodHookParam.args[0];
                Object arg2 = methodHookParam.args[1];
                Object arg3 = methodHookParam.args[2];
                Object arg4 = methodHookParam.args[3];
                Object arg5 = methodHookParam.args[4];
                Object arg6 = methodHookParam.args[5];
                Object arg7 = methodHookParam.args[6];
                Object arg8 = methodHookParam.args[7];
                Object arg9 = methodHookParam.args[8];
                XposedBridge.log("arg-" + arg);
                XposedBridge.log("arg2-" + arg2);
                XposedBridge.log("arg3-" + arg3);
                XposedBridge.log("arg4-" + arg4);
                XposedBridge.log("arg5-" + arg5);
                XposedBridge.log("arg6-" + arg6);
                XposedBridge.log("arg7-" + arg7);
                XposedBridge.log("arg8-" + arg8);
                XposedBridge.log("arg9-" + arg9);
            }
        });


    }

    /**
     * @description 尝试hook触摸精灵
     * @author zhengyx
     * @date 2017/4/24
     */
    private void tryTohookTouchSpiriteService(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        final ClassLoader classLoader = loadPackageParam.classLoader;
        String className = "net.aisence.Touchelper.TouchelperService";
        final String method = "onStartCommand";
        XposedHelpers.findAndHookMethod(className, classLoader, method, Intent.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                XposedBridge.log("hacked in touchSpirit  service onstartcommand");
                runScript(methodHookParam.thisObject, classLoader);
//                Intent intent = (Intent) methodHookParam.args[0];
//                Bundle intentBundle = intent.getExtras();
//                Set<String> keySet = intentBundle.keySet();
//                int count = 0;
//                StringBuilder stringBuilder = new StringBuilder();
//                for (String key : keySet) {
//                    count++;
//                    Object thisObject = intentBundle.get(key);
//                    stringBuilder.append(" Bundle EXTRA ").append(count)
//                            .append(":");
//                    String thisClass = thisObject.getClass().getName();
//                    if (thisClass != null) {
//                        stringBuilder.append("Bundle Class: ").append(thisClass)
//                                .append(NEWLINE);
//                    }
//                    stringBuilder.append(" Bundle Key: ").append(key).append(NEWLINE);
//
//                    if (thisObject instanceof String || thisObject instanceof Long
//                            || thisObject instanceof Integer
//                            || thisObject instanceof Boolean) {
//                        stringBuilder.append(" Bundle Value: " + thisObject.toString())
//                                .append(NEWLINE);
//                    } else if (thisObject instanceof ArrayList) {
//                        stringBuilder.append(" Bundle Values:");
//                        ArrayList thisArrayList = (ArrayList) thisObject;
//                        for (Object thisArrayListObject : thisArrayList) {
//                            stringBuilder.append(thisArrayListObject.toString()
//                                    + NEWLINE);
//                        }
//                    }
//                }
//                XposedBridge.log("  spirit script Bundle EXTRA:" + stringBuilder);
            }
        });


    }


    public void runScript(Object thisObject, ClassLoader classLoader) {

        try {
            Class<?> threadClazz = classLoader.loadClass("net.aisence.Touchelper.TouchelperBin");
//Object paramObject,  String paramString1, String paramString2,
//  long paramLong,     String paramString3, String paramString4,
//  String paramString5, String paramString6, boolean paramBoolean

//            04-24 16:33:30.207 I/Xposed  ( 9130): arg-net.aisence.Touchelper.TouchelperService@32514413
//            04-24 16:33:30.208 I/Xposed  ( 9130): arg2-volume+
//                    04-24 16:33:30.208 I/Xposed  ( 9130): arg3-/mnt/sdcard/touchelf/scripts/wxcircle.lua
//            04-24 16:33:30.208 I/Xposed  ( 9130): arg4--1
//            04-24 16:33:30.208 I/Xposed  ( 9130): arg5-
//                    04-24 16:33:30.208 I/Xposed  ( 9130): arg6-192.168.1.163
//            04-24 16:33:30.208 I/Xposed  ( 9130): arg7-com.sohu.inputmethod.sogou/.SogouIME
//            04-24 16:33:30.208 I/Xposed  ( 9130): arg8-flinger
//            04-24 16:33:30.208 I/Xposed  ( 9130): arg9-true

            Method method = threadClazz.getMethod("doPlay",
                    Object.class, String.class, String.class,
                    long.class, String.class, String.class,
                    String.class, String.class, boolean.class);
            Object invoke = method.invoke(null, thisObject, "volume+", "/mnt/sdcard/touchelf/scripts/wxcircle.lua",
                    1, "", "",
                    "com.sohu.inputmethod.sogou/.SogouIME", "flinger", true
            );

            XposedBridge.log(" run script reflect result:" + invoke);

        } catch (ClassNotFoundException e) {
           XposedBridge.log(e);
        } catch (NoSuchMethodException e) {
            XposedBridge.log(e);
        } catch (IllegalAccessException e) {
            XposedBridge.log(e);
        } catch (InvocationTargetException e) {
            XposedBridge.log(e);
        }
    }


}
