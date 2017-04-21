package com.test.emptydemo;

import android.content.Context;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

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
        }

    }
}
