package com.test.emptydemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView {
 public float oldY;
 private int t;
 
 public MyWebView(Context context) {
 super(context);
 init();
 }
 
 public MyWebView(Context context, AttributeSet attrs) {
 super(context, attrs);
 init();
 }
 
 public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
 super(context, attrs, defStyleAttr);
 init();
 }
 
 private void init() {
 WebSettings settings = getSettings();
 settings.setJavaScriptEnabled(true);
 setWebViewClient(new WebViewClient() {
  @Override
  public boolean shouldOverrideUrlLoading(WebView view, String url) {
  view.loadUrl(url);
  return true;
  }
 });
 }
 
 @Override
 public boolean onTouchEvent(MotionEvent ev) {
 getParent().getParent().requestDisallowInterceptTouchEvent(true);
 switch (ev.getAction()) {
  case MotionEvent.ACTION_DOWN:
  oldY = ev.getY();
  break;
  case MotionEvent.ACTION_MOVE:
 
  float Y = ev.getY();
  float Ys = Y - oldY;
  if (Ys > 0 && t == 0) {
   getParent().getParent().requestDisallowInterceptTouchEvent(false);
  }
  break;
 
 }
 
 return super.onTouchEvent(ev);
 }
 
 @Override
 protected void onScrollChanged(int l, int t, int oldl, int oldt) {
 this.t = t;
 super.onScrollChanged(l, t, oldl, oldt);
 }
 
}