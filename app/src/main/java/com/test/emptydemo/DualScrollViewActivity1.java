package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class DualScrollViewActivity1 extends Activity implements View.OnClickListener {
 
 private MyWebView webView;
 
 private TextView sinaTv, qqTv, baiduTv;

 private final String BAIDU = "http://www.baidu.com";
 private final String QQ = "http://www.qq.com";
 private final String SINA = "http://sina.cn";
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 requestWindowFeature(Window.FEATURE_NO_TITLE);
 InitView();
 sinaTv.performClick();
 }
 
 private void InitView() {
 setContentView(R.layout.dual_scrollview_activity_layout1);
 webView = (MyWebView) findViewById(R.id.web);
 
 sinaTv = (TextView) findViewById(R.id.one);
 sinaTv.setOnClickListener(this);
 qqTv = (TextView) findViewById(R.id.two);
 qqTv.setOnClickListener(this);
 baiduTv = (TextView) findViewById(R.id.three);
 baiduTv.setOnClickListener(this);
 
 }
 
 @Override
 public void onClick(View v) {
 reset();
 String url = "";
 switch (v.getId()) {
  case R.id.one:
  url = SINA;
  break;
  case R.id.two:
  url = QQ;
  break;
  case R.id.three:
  url = BAIDU;
  break;
  default:
  break;
 }
 
 webView.loadUrl(url);
 }
 
 private void reset(){
 }
}