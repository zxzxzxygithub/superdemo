package com.test.emptydemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author zhengyx
 * @description 自带动画的启动页view
 * @date 2017/3/1
 */
public class ModelView extends RelativeLayout {

    private int mBigPicDrawableId = -1;
    private int DEFAULTRESOURCEID = -1;
    private View mContainerView;
    private View mCenterIcon;
    private View mCircle;
    private View mBigPicView;

    public ModelView(Context context) {
        super(context);
        init(null);
    }

    public ModelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_modelshow, null);
        mBigPicView = view.findViewById(R.id.bg);
        mContainerView = view.findViewById(R.id.rlbg);
        mCenterIcon = view.findViewById(R.id.centericon);
        mCircle = view.findViewById(R.id.circle);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.welcomeattr);
            int resourceId = typedArray.getResourceId(0, -1);
            if (resourceId!=-1){
                mBigPicView.setBackgroundResource(resourceId);
            }
        }
        addView(view);
    }

    public void setBigPicDrawableId(int drawableId) {
        mBigPicDrawableId = drawableId;
    }

    public void startAni() {
        WelcomeAniManager welcomAniManager = new WelcomAniManagerImpl(getContext());
        welcomAniManager.showWelcomAni(mBigPicView, mCenterIcon, mContainerView, mCircle);
    }
}
