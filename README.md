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


本demo参考：https://github.com/hongyangAndroid/baseAdapter

https://github.com/jianghejie/XRecyclerView