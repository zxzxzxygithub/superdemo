package com.test.emptydemo.xposed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.test.emptydemo.Utils;

import java.lang.reflect.Method;

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
    private  static final int GROUP_ID = 11;//hooked menugroupid
    private  static final int ITERM_ID = 11;//hooked menugroupid

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
// 过滤掉非微信
//        if (!loadPackageParam.packageName.equals("com.tencent.mm")) {
//            return;
//        }


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
        XposedHelpers.findAndHookMethod("android.app.Activity", loadPackageParam.classLoader, "onCreateOptionsMenu", Menu.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                Menu menu = (Menu) methodHookParam.args[0];
                int groupId = 1;
                int itemId = 1;
                int order = 1;
                String title ="私聊微x模块";
                menu.add(groupId, itemId, order, title);
                XposedBridge.log("onCreateOptionsMenu");
                Log.d("demotestxp", "onCreateOptionsMenu");

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

                int order = 1;
                String title ="Zhengyx微x模块";
                menu.add(GROUP_ID, ITERM_ID, order, title);
                XposedBridge.log("onCreateOptionsMenu");
                Log.d("demotestxp", "onCreateOptionsMenu");

            }
        });
        XposedHelpers.findAndHookMethod("android.app.Activity", loadPackageParam.classLoader, "onOptionsItemSelected", MenuItem.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                MenuItem menuItem = (MenuItem) methodHookParam.args[0];
                int groupId = menuItem.getGroupId();
                if (groupId==GROUP_ID){
                    int itemId = menuItem.getItemId();
                    if (itemId==ITERM_ID){
                        Toast.makeText((Context) methodHookParam.thisObject, "hello wechat", Toast.LENGTH_LONG).show();
                    }
                    XposedBridge.log("oncreate inable dotmenu");
                    Log.d("demotestxp", "oncreate enable dotmenu");
                }

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
                XposedBridge.log("onMenuOpened beforeHookedMethod _featureId_" + featureId);
                Log.d("demotestxp", "onMenuOpened beforeHookedMethod _featureId_" + featureId);
                if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
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
        });


    }
}
