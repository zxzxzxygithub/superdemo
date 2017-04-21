package com.test.emptydemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by zhengyongxiang on 2017/4/20.
 */

public class Utils {

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
}
