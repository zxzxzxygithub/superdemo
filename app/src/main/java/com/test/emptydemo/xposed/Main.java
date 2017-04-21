package com.test.emptydemo.xposed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.test.emptydemo.Utils;

import java.io.File;
import java.lang.reflect.Method;

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


        final String packageName = "com.test.emptydemo";
        String filePath = String.format("/data/app/%s-%d/base.apk", packageName, 1);
        if (!new File(filePath).exists()) {
            filePath = String.format("/data/app/%s-%d/base.apk", packageName, 2);
            if (!new File(filePath).exists()) {
                XposedBridge.log("Error:在/data/app找不到APK文件" + packageName);
                return;
            }
        }
        final PathClassLoader pathClassLoader = new PathClassLoader(filePath, ClassLoader.getSystemClassLoader());
        final Class<?> aClass = Class.forName("com.test.emptydemo.xposed.Main", true, pathClassLoader);
        final Method aClassMethod = aClass.getMethod("handleLoadPackage4release", XC_LoadPackage.LoadPackageParam.class);
        aClassMethod.invoke(aClass.newInstance(), loadPackageParam);
    }

    public void handleLoadPackage4release(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
// 过滤掉非微信
        if (!loadPackageParam.packageName.equals("com.tencent.mm")) {
            return;
        }

        //        hook telephonymanager的getDeviceId方法，修改返回值为11111111111
        XposedHelpers.findAndHookMethod("android.app.Activity", loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                Context context = (Context) methodHookParam.thisObject;
                Utils.showDotMenu(context);
                XposedBridge.log("oncreate inable dotmenu");
                Log.d("demotestxp", "oncreate enable dotmenu");
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);

            }
        });
        XposedHelpers.findAndHookMethod("android.app.Fragment", loadPackageParam.classLoader, "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                Context context = (Context) methodHookParam.thisObject;
                Utils.showDotMenu(context);
                XposedBridge.log("Fragment oncreate inable dotmenu");
                Log.d("demotestxp", "Fragment oncreate enable dotmenu");
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);

            }
        });
        XposedHelpers.findAndHookMethod("android.support.v4.app.Fragment", loadPackageParam.classLoader, "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                Utils.showDotMenuInFragment();
                XposedBridge.log("android.support.v4.app.Fragment oncreate inable dotmenu");
                Log.d("demotestxp", "Fragment oncreate enable dotmenu");
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);

            }
        });

        XposedHelpers.findAndHookMethod("android.app.Activity", loadPackageParam.classLoader, "onMenuItemSelected", int.class, MenuItem.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                MenuItem menuItem = (MenuItem) methodHookParam.args[1];
                int groupId = menuItem.getGroupId();
                int itemId = menuItem.getItemId();
                if (groupId == GROUP_ID) {
                    if (itemId == ITERM_ID) {
                        Toast.makeText((Context) methodHookParam.thisObject, "onMenuItemSelected-hello wechat", Toast.LENGTH_LONG).show();
                    }
                }
                XposedBridge.log("onMenuItemSelected_groupid_" + groupId + "_itemid_" + itemId);
                Log.d("demotestxp", "onMenuItemSelected_groupid_" + groupId + "_itemid_" + itemId);

            }
        });

//        hook telephonymanager的getDeviceId方法，修改返回值为11111111111
        XposedHelpers.findAndHookMethod(Activity.class, "onMenuOpened", int.class, Menu.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                int featureId = (Integer) methodHookParam.args[0];
                Menu menu = (Menu) methodHookParam.args[1];
                if (menu != null) {
                    XposedBridge.log("onMenuOpened beforeHookedMethod _featureId_itemid_" + featureId + "-" + menu.getItem(0).getItemId());
                    Log.d("demotestxp", "onMenuOpened beforeHookedMethod _featureId_" + featureId);
                    if (featureId == Window.FEATURE_ACTION_BAR) {
                        if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                            try {
                                Method m = menu.getClass().getDeclaredMethod(
                                        "setOptionalIconsVisible", Boolean.TYPE);
                                m.setAccessible(true);
                                m.invoke(menu, true);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        });


        XposedHelpers.findAndHookMethod("android.app.Activity", loadPackageParam.classLoader, "onPrepareOptionsMenu", Menu.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                Menu menu = (Menu) methodHookParam.args[0];
                try {
                    menu.removeGroup(GROUP_ID);
                } catch (Exception e) {
                    e.printStackTrace();
                    XposedBridge.log(e);
                }

                int order = 0;
                String title = "123Zhengyx微x模块";
                menu.add(GROUP_ID, ITERM_ID, order, title);
                XposedBridge.log("onPrepareOptionsMenu");
                Log.d("demotestxp", "onPrepareOptionsMenu");

            }
        });
        XposedBridge.log("------------5");
        Log.d("testnoreboot", "handleLoadPackage4release: 123456");
    }
}
