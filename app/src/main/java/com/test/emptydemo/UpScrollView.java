package com.test.emptydemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ScrollView;

public class UpScrollView extends ScrollView {
 /**
 * 屏幕高度
 */
 private int mScreenHeight;
 /**
 * 上一次的坐标
 */
 private float mLastY;
 /**
 * 当前View滑动距离
 */
 private int mScrollY;
 /**
 * 当前View内子控件高度
 */
 private int mChildH;
 
 public UpScrollView(Context context) {
 super(context);
 init(context);
 }
 
 public UpScrollView(Context context, AttributeSet attrs) {
 super(context, attrs);
 init(context);
 }
 
 public UpScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
 super(context, attrs, defStyleAttr);
 init(context);
 
 }
 
 private void init(Context context) {
 WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
 DisplayMetrics dm = new DisplayMetrics();
 wm.getDefaultDisplay().getMetrics(dm);
 mScreenHeight = dm.heightPixels;
 }
 
 @Override
 public boolean onTouchEvent(MotionEvent ev) {
 //默认设定顶层View不拦截
 
 getParent().getParent().requestDisallowInterceptTouchEvent(true);
 
 switch (ev.getAction()) {
  case MotionEvent.ACTION_DOWN:
  mLastY = (int) ev.getY();
  break;
  case MotionEvent.ACTION_MOVE:
  float y = ev.getY();
  float deltaY = y - mLastY;
 
  mChildH = this.getChildAt(0).getMeasuredHeight();
 
  int translateY = mChildH - mScrollY;
 
  //向上滑动时，如果translateY等于屏幕高度时，即表明滑动到底部，可又顶层View控制滑动
  if (deltaY < 0 && translateY == mScreenHeight) {
   getParent().getParent().requestDisallowInterceptTouchEvent(false);
  }
  break;
  default:
  break;
 
 }
 
 return super.onTouchEvent(ev);
 }
 
 @Override
 protected void onScrollChanged(int l, int t, int oldl, int oldt) {
 super.onScrollChanged(l, t, oldl, oldt);
 mScrollY = t;
 }
}