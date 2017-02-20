package com.test.emptydemo.multiadapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.emptydemo.R;
import com.test.emptydemo.bean.ChatMessage;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * Created by zhy on 16/6/22.
 */
public class ViewpagerItemDelagate implements ItemViewDelegate<ChatMessage> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.complexone;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        ArrayList<String> list = item.getList();
        boolean isempty = list == null || list.isEmpty();
        return !isempty;
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage viewPagerData, int position) {

        ViewPager viewPager = holder.getView(R.id.item_detail_viewpager);

        ArrayList<String> list = viewPagerData.getList();

        viewPager.setAdapter(new MyViewPagerAdapter(list));

    }

    public class MyViewPagerAdapter extends PagerAdapter {

        private final ArrayList<String> contentViews;

        public MyViewPagerAdapter(ArrayList<String> contentViews) {
            this.contentViews = contentViews;

        }

//        ArrayList<View> mViewList = new ArrayList<>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
//            if (mViewList.isEmpty()) {
                view = LayoutInflater.from(container.getContext()).inflate(R.layout.item, null);
                TextView textView = (TextView) view.findViewById(R.id.text);
                textView.setText(contentViews.get(position));
//            } else {
//                view = mViewList.remove(0);
//            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }


        @Override
        public int getCount() {
            return contentViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}
