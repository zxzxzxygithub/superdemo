##  仿apidemo做的demo框架，在manifest里面配置步骤如下

1. 为activity设置lable，lable就是路径，用/隔开，比如  app/test

2. 为activity配置action和category，按如下配置

```
<intent-filter>
<action android:name="android.intent.action.MAIN" />
<category android:name="android.intent.category.SAMPLE_CODE" />
</intent-filter>
```

3.  如果手机装有apidemo则会列出apidemo中的所有列表
    同理如果你的demo都是遵循这个原则也会把你其他demo的列表都列进来

4. 本demo使用了butterknife，具体配置如下

项目的build.gradle

```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.3'
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
```

app的build.gradle

```

apply plugin: 'com.neenbedankt.android-apt'

dependencies {

    compile 'com.jakewharton:butterknife:8.4.0'
    apt 'com.jakewharton:butterknife-compiler:8.4.0'
}
```

动态菜单修改，参考 http://blog.csdn.net/pengruikeji/article/details/6432807