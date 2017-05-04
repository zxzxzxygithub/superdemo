package com.test.emptydemo.xposed;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.test.emptydemo.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
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

    //带参数的方法拦截
    private void hook_methods(String className, String methodName, XC_MethodHook xmh){
        try {
            Class<?> clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods())
                if (method.getName().equals(methodName)
                        && !Modifier.isAbstract(method.getModifiers())
                        && Modifier.isPublic(method.getModifiers())) {
                    XposedBridge.hookMethod(method, xmh);
                }
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        try {
            handleLoadPackage4release(loadPackageParam);
        } catch (Exception e) {
            XposedBridge.log(e);
        }

    }

    public void handleLoadPackage4release(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
// 过滤掉非微信
        if (!loadPackageParam.packageName.equals("com.tencent.mm")) {
            return;
        }
        hookLocation3(loadPackageParam);
      //  hookWxAddress(loadPackageParam);
       // hookDialog(loadPackageParam);
        XposedBridge.log("---handleLoadPackage4release---------5");
        Log.d("testnoreboot", "handleLoadPackage4release: 123456");
    }


    private void hookLocation(XC_LoadPackage.LoadPackageParam loadPackageParam) {
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

    }
    private void hookLocation3(XC_LoadPackage.LoadPackageParam mLpp) {
        hook_method("android.net.wifi.WifiManager", mLpp.classLoader, "getScanResults",
                new XC_MethodHook(){
                    /**
                     * Android提供了基于网络的定位服务和基于卫星的定位服务两种
                     * android.net.wifi.WifiManager的getScanResults方法
                     * Return the results of the latest access point scan.
                     * @return the list of access points found in the most recent scan.
                     */
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        //返回空，就强制让apps使用gps定位信息
                        param.setResult(null);
                        XposedBridge.log("------------getScanResults ------------------ \n ".toUpperCase());
                        XposedBridge.log("------------getScanResults ------------------ \n ".toUpperCase());
                    }
                });

        hook_method("android.telephony.TelephonyManager", mLpp.classLoader, "getCellLocation",
                new XC_MethodHook(){
                    /**
                     * android.telephony.TelephonyManager的getCellLocation方法
                     * Returns the current location of the device.
                     * Return null if current location is not available.
                     */
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        param.setResult(null);
                        XposedBridge.log("------------getCellLocation ------------------ \n ".toUpperCase());
                        XposedBridge.log("------------getCellLocation ------------------ \n ".toUpperCase());
                    }
                });

        hook_method("android.telephony.TelephonyManager", mLpp.classLoader, "getNeighboringCellInfo",
                new XC_MethodHook(){
                    /**
                     * android.telephony.TelephonyManager类的getNeighboringCellInfo方法
                     * Returns the neighboring cell information of the device.
                     */
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        param.setResult(null);
                        XposedBridge.log("------------getNeighboringCellInfo ------------------ \n ".toUpperCase());
                        XposedBridge.log("------------getNeighboringCellInfo ------------------ \n ".toUpperCase());
                    }
                });

        hook_methods("android.location.LocationManager", "requestLocationUpdates",
                new XC_MethodHook() {
                    /**
                     * android.location.LocationManager类的requestLocationUpdates方法
                     * 其参数有4个：
                     * String provider, long minTime, float minDistance,LocationListener listener
                     * Register for location updates using the named provider, and a pending intent
                     */
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("------------requestLocationUpdates ------------------ \n ".toUpperCase());
                        XposedBridge.log("------------requestLocationUpdates ------------------ \n ".toUpperCase());
                        if (param.args.length == 4 && (param.args[0] instanceof String)) {
                            //位置监听器,当位置改变时会触发onLocationChanged方法
                            LocationListener ll = (LocationListener)param.args[3];

                            Class<?> clazz = LocationListener.class;
                            Method m = null;
                            for (Method method : clazz.getDeclaredMethods()) {
                                if (method.getName().equals("onLocationChanged")) {
                                    m = method;
                                    break;
                                }
                            }

                            try {
                                if (m != null) {
                                    Object[] args = new Object[1];
                                    Location l = new Location(LocationManager.GPS_PROVIDER);
                                    //台北经纬度:121.53407,25.077796
                                    double la=121.53407;
                                    double lo=25.077796;
                                    l.setLatitude(la);
                                    l.setLongitude(lo);
                                    args[0] = l;
                                    m.invoke(ll, args);
                                    XposedBridge.log("fake location: " + la + ", " + lo);
                                }
                            } catch (Exception e) {
                                XposedBridge.log(e);
                            }
                        }
                    }
                });


        hook_methods("android.location.LocationManager", "getGpsStatus",
                new XC_MethodHook(){
                    /**
                     * android.location.LocationManager类的getGpsStatus方法
                     * 其参数只有1个：GpsStatus status
                     * Retrieves information about the current status of the GPS engine.
                     * This should only be called from the {@link GpsStatus.Listener#onGpsStatusChanged}
                     * callback to ensure that the data is copied atomically.
                     *
                     */
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("------------getGpsStatus ------------------ \n ".toUpperCase());
                        XposedBridge.log("------------getGpsStatus ------------------ \n ".toUpperCase());
                        GpsStatus gss = (GpsStatus)param.getResult();
                        if (gss == null)
                            return;

                        Class<?> clazz = GpsStatus.class;
                        Method m = null;
                        for (Method method : clazz.getDeclaredMethods()) {
                            if (method.getName().equals("setStatus")) {
                                if (method.getParameterTypes().length > 1) {
                                    m = method;
                                    break;
                                }
                            }
                        }
                        m.setAccessible(true);
                        //make the apps belive GPS works fine now
                        int svCount = 5;
                        int[] prns = {1, 2, 3, 4, 5};
                        float[] snrs = {0, 0, 0, 0, 0};
                        float[] elevations = {0, 0, 0, 0, 0};
                        float[] azimuths = {0, 0, 0, 0, 0};
                        int ephemerisMask = 0x1f;
                        int almanacMask = 0x1f;
                        //5 satellites are fixed
                        int usedInFixMask = 0x1f;
                        try {
                            if (m != null) {
                                m.invoke(gss,svCount, prns, snrs, elevations, azimuths, ephemerisMask, almanacMask, usedInFixMask);
                                param.setResult(gss);
                            }
                        } catch (Exception e) {
                            XposedBridge.log(e);
                        }
                    }
                });

    }


    private void hookLocation2(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod("com.android.server.LocationManagerService", lpparam.classLoader, "reportLocation", Location.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Location location = (Location) param.args[0];
                XposedBridge.log("实际 系统 纬度" + location.getLatitude() + " 系统 精度" + location.getLongitude() + "系统 加速度 " + location.getAccuracy());
                XSharedPreferences xsp = new XSharedPreferences("com.markypq.gpshook", "markypq");
                if (xsp.getBoolean("enableHook", true)) {
                    double latitude = Double.valueOf(xsp.getString("lan", "117.536246")) + (double) new Random().nextInt(1000) / 1000000;
                    double longtitude = Double.valueOf(xsp.getString("lon", "36.681752")) + (double) new Random().nextInt(1000) / 1000000;
                    location.setLongitude(longtitude);
                    location.setLatitude(latitude);
                    XposedBridge.log("hook 系统 经度" + location.getLatitude() + " 系统 纬度" + location.getLongitude() + "系统 加速度 " + location.getAccuracy());
                }

            }
        });

    }


    /**
     * @description 微信通讯录页面
     * @author zhengyx
     * @date 2017/4/23
     */
    private void hookWxAddress(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
//        在onresume方法中调用vw方法，切换到通讯录界面获取通讯录
        final ClassLoader classLoader = loadPackageParam.classLoader;
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUI", classLoader, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                XposedBridge.log(("-------------------------------------------\n" + "hook address start turnto address tab\n" +
                        "-----------------------------------------------").toUpperCase());
                XposedBridge.log(("-------------------------------------------\n" + "hook address start turnto address tab\n" +
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
                Method changeTabMethod = mainUiClass.getDeclaredMethod("vw", int.class);
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
