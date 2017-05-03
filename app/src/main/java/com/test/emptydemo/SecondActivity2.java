package com.test.emptydemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhengyx
 * @description 注册lable为1test/test
 * @date 2017/2/8
 */
public class SecondActivity2 extends Activity implements View.OnClickListener {
    Fragment fragment;
    @BindView(R.id.tv)
    TextView tv;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        tv.setText("try to click me");
        Utils.showDotMenu(this);
    }

    @OnClick(R.id.tv)
    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.tv:
                writeOtherAppSpWithFileWriting();
                break;
        }


    }

    /**
     * @description 直接通过文件写入的方式操作其他app的sp文件
     * @author zhengyx
     * @date 2017/5/3
     */
    private void writeOtherAppSpWithFileWriting() {
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

    /**
     * @description 在其他app的sp中写入值，暂时失败
     * @author zhengyx
     * @date 2017/5/3
     */
    @Deprecated
    private void writeOtherAppSpWithContext() {
        try {
            context = this.createPackageContext("de.robv.android.xposed.installer", Context.CONTEXT_IGNORE_SECURITY);
            SharedPreferences sharedPreferences = context.getSharedPreferences("enabled_modules",
                    MODE_MULTI_PROCESS | MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("com.test.emptydemo", 1).apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int groupId = 1;
        int itemId = 1;
        int order = 1;
        String title = "this is an addmenu";
        menu.add(groupId, itemId, order, title);
        menu.add("menu 2");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
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
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "clickitem_" + item.getItemId(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        Toast.makeText(this, "onMenuItemSelected_" + item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onMenuItemSelected(featureId, item);
    }
}
