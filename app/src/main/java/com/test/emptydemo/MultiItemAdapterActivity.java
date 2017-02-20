package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.test.emptydemo.bean.ChatMessage;
import com.test.emptydemo.multiadapter.ChatAdapterForRv;
import com.test.emptydemo.multiadapter.ViewpagerItemDelagate;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhengyx
 * @date 2017/2/8
 */
public class MultiItemAdapterActivity extends Activity {

    private List<ChatMessage> mDatas = new ArrayList<>();
    private ArrayList<String> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headerfooter);
        initData();
        final XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.xrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);


        //        first header
        View header = LayoutInflater.from(this).inflate(R.layout.complextwo, null);
        xRecyclerView.addHeaderView(header);

//      second header  headerView需要最外层包裹一个linearlayout，否则只有viewpager是无法显示的
        View headerView = LayoutInflater.from(this).inflate(R.layout.complexone, null);
//      setviewpager
        xRecyclerView.addHeaderView(headerView);
        setViewPager(headerView);


        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        mDatas.addAll(ChatMessage.MOCK_DATAS);
        ChatAdapterForRv adapter = new ChatAdapterForRv(this, mDatas);

        xRecyclerView.setAdapter(adapter);


    }

    private void initData() {
        list1 = new ArrayList<>();
        list1.add("header左右滑动1");
        list1.add("header左右滑动2");
        list1.add("header左右滑动3");
    }


    private void setViewPager(View header) {
        ViewPager viewPager = (ViewPager) header.findViewById(R.id.item_detail_viewpager);

        viewPager.setAdapter(new MyViewPagerAdapter(list1));

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
