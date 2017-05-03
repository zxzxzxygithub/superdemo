package com.test.emptydemo.xposed;

import android.app.Activity;
import android.content.ComponentName;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.test.emptydemo.SuperService;
import com.test.emptydemo.Utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
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
    private Context applicationContext;

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

    }

    public void handleLoadPackage4release(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
// 过滤掉非微信
        if (!loadPackageParam.packageName.equals("com.tencent.mm")) {
            return;
        }
        hookWxAddress(loadPackageParam);
        hookDialog(loadPackageParam);
        XposedBridge.log("---handleLoadPackage4release---------5");
        Log.d("testnoreboot", "handleLoadPackage4release: 123456");
    }


    /**
     * @description 微信通讯录页面
     * @author zhengyx
     * @date 2017/4/23
     */
    private void hookWxAddress(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        final ClassLoader classLoader = loadPackageParam.classLoader;
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUI", classLoader, "onResume",  new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                XposedBridge.log(("-------------------------------------------\n" +"hook address start turnto address tab\n" +
                                "-----------------------------------------------").toUpperCase());
                XposedBridge.log(("-------------------------------------------\n" +"hook address start turnto address tab\n" +
                                "-----------------------------------------------").toUpperCase());
//
                Object thisObject = methodHookParam.thisObject;
//
                Class<?> mainUiClass = classLoader.loadClass("com.tencent.mm.ui.LauncherUI");
//                Field nCPField = mainUiClass.getDeclaredField("nCP");
//                nCPField.setAccessible(true);
//                Object ncpInstance = nCPField.get(mainUiClass.newInstance());
//                Class<?> ncpClass = classLoader.loadClass("com.tencent.mm.ui.mogic.WxViewPager");
//                Method kMethod = ncpClass.getMethod("k", int.class, boolean.class);
//                kMethod.invoke(ncpInstance,1,false);
//
                Method changeTabMethod = mainUiClass.getDeclaredMethod("vw",int.class);
                changeTabMethod.setAccessible(true);
                changeTabMethod.invoke(thisObject, 1);
//                XposedHelpers.callMethod(thisObject,"vx",1);
                XposedBridge.log(("-------------------------------------\n" +
                        "hook address start turnto address tab succeeded \n" +
                        "----------------------------------------").toUpperCase());
                XposedBridge.log(("--------------------------------------\n" +
                        "hook address start turnto address tab succeeded \n" +
                        "-----------------------------------------").toUpperCase());
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);

            }
        });

        try {
            Class<?> ContextClass = XposedHelpers.findClass("android.content.ContextWrapper", classLoader);
            XposedHelpers.findAndHookMethod(ContextClass, "getApplicationContext", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (applicationContext != null)
                        return;
                    applicationContext = (Context) param.getResult();
                    XposedBridge.log("CSDN_LQR-->得到上下文");
                }
            });
        } catch (Throwable t) {
            XposedBridge.log("CSDN_LQR-->获取上下文出错");
            XposedBridge.log(t);
        }
//        通讯录 notifydatasetchanged方法在这里调用的
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.contact.AddressUI$a", classLoader, "byt", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
//                    通讯录的类是  com.tencent.mm.ui.contact.AddressUI$a
                Object currentObject = methodHookParam.thisObject;
                Field gQlField = currentObject.getClass().getDeclaredField("gQL");
                gQlField.setAccessible(true);
                String name = gQlField.getName();
                XposedBridge.log("AddressUI$a  byt_ findlistview 实例_" + name);
                ListView listview = (ListView) gQlField.get(currentObject);
                ListAdapter adapter = listview.getAdapter();
                int count = adapter.getCount();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < count; i++) {
//                    已经有类的实例
                    Object item = adapter.getItem(i);
                    if (item != null) {
                        XposedBridge.log("AddressUI$a  byt_ findlistviewitem_i_" + i + "_" +
                                item);
//                       反射获取类的变量的值
                        Class<?> beanClass = classLoader.loadClass("com.tencent.mm.storage.f");
                        Field[] declaredFields = beanClass.getDeclaredFields();
                        for (Field field :
                                declaredFields) {
//                            变量名
                            String fieldName = field.getName();
//                             变量的值
                            Object value = field.get(item);
                            String wxAddressItemContent = "fieldname_" + fieldName + "_value_" + value + "\n";
                            stringBuilder.append(wxAddressItemContent);
                            XposedBridge.log(wxAddressItemContent);
                        }


                    }
                }
                if (applicationContext != null) {
                    Intent serviceIntent = new Intent();
                    serviceIntent.setComponent(new ComponentName("com.test.emptydemo", "com.test.emptydemo.SuperService"));
                    serviceIntent.putExtra("wxa", stringBuilder.toString());
                    applicationContext.startService(serviceIntent);
                } else {
                    XposedBridge.log("applicationContext is null");
                }
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);


            }
        });
        XposedHelpers.findAndHookMethod("com.tencent.mm.storage.f", classLoader, "b", Cursor.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                Cursor cursor = (Cursor) methodHookParam.args[0];
                XposedBridge.log("hook com.tencent.mm.storage.f _cursorindex0_" + cursor.getString(0));

            }
        });
    }

    private void hookMenu(XC_LoadPackage.LoadPackageParam loadPackageParam) {
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
                        Toast.makeText((Context) methodHookParam.thisObject, "000_onMenuItemSelected-hello wechat", Toast.LENGTH_LONG).show();
                    }
                }
                XposedBridge.log("onMenuItemSelected_groupid_title_" + groupId + "_itemid_" + itemId + "_" + menuItem.getTitle());
                Log.d("demotestxp", "onMenuItemSelected_groupid_" + groupId + "_itemid_" + itemId);

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
                int itemId = menuItem.getItemId();
                if (groupId == GROUP_ID) {
                    if (itemId == ITERM_ID) {
                        Toast.makeText((Context) methodHookParam.thisObject, "onOptionsItemSelected-hello wechat", Toast.LENGTH_LONG).show();
                    }
                }
                XposedBridge.log("onOptionsItemSelected_" + groupId + "_itemid_" + itemId + "_" + menuItem.getTitle());
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
    }

    private void hookDialog(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod("android.app.Dialog", loadPackageParam.classLoader, "show", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                XposedBridge.log("Dialog show ");
                Log.d("demotestxp", "alerdialog show");


            }
        });
        XposedHelpers.findAndHookMethod("android.app.AlertDialog", loadPackageParam.classLoader, "show", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);
                XposedBridge.log("AlertDialog show ");
                Log.d("demotestxp", "alerdialog show");


            }
        });
    }

    private void tryTohookWxModule(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod("com.fkzhang.xposed.hook.WxCoreLoader", loadPackageParam.classLoader, "a", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                XposedBridge.log("WxCoreLoader  a");

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);


            }
        });
        XposedHelpers.findAndHookMethod("com.fkzhang.xposed.hook.WxCoreLoader", loadPackageParam.classLoader, "b", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                XposedBridge.log("WxCoreLoader  c");

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);


            }
        });
        XposedHelpers.findAndHookMethod("com.fkzhang.xposed.hook.WxCoreLoader", loadPackageParam.classLoader, "c", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                XposedBridge.log("WxCoreLoader  c");

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);


            }
        });
        XposedHelpers.findAndHookConstructor("com.fkzhang.xposed.hook.WxCoreLoader", loadPackageParam.classLoader, ClassLoader.class, Context.class, Context.class, ContentValues.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                XposedBridge.log("WxCoreLoader  constructor ");

                ContentValues contentValues = (ContentValues) methodHookParam.args[3];
                Set<Map.Entry<String, Object>> entries = contentValues.valueSet();
                for (Map.Entry<String, Object> map :
                        entries) {
                    String key = map.getKey();
                    Object value = map.getValue();
                    XposedBridge.log("listontentvalue key_" + key + "_value_" + value);
                    Log.d("listontentvalue", "onCreate: key_" + key + "_value_" + value);
                }

            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);


            }
        });
    }

    /**
     * @description 微信首页
     * @author zhengyx
     * @date 2017/4/23
     */
    private void hookWxMainPage(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUI", loadPackageParam.classLoader, "vx", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                if ((Integer) methodHookParam.args[0] == 1) {
//                    通讯录的类是  com.tencent.mm.ui.contact.AddressUI$a
                    XposedBridge.log("LauncherUI  vx   result_ " + methodHookParam.getResult().getClass());
                    Log.d("demotestxp", "oncreate enable dotmenu");
                }
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.beforeHookedMethod(methodHookParam);

            }
        });
    }
}
