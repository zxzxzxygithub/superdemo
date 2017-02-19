package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;


/**
 * @author zhengyx
 * @date 2017/2/8
 */
public class RefreshRvActivity extends Activity {


    static ArrayList<String> list = new ArrayList<>();

    static {
        for (int i = 0; i < 100; i++) {

            list.add("我是item a" + i);
        }
    }

    private MyAdapter myAdapter;
    private static final  int CHANGEDPOSITION=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headerfooter);
        final XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.xrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        此处设置刷新样式
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        此处设置刷新箭头
        xRecyclerView.setArrowImageView(R.mipmap.ic_launcher);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(false);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        list.set(CHANGEDPOSITION, "113freshed ~~");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                很奇怪这行没有生效
//                                myAdapter.notifyItemChanged(changedPosition);
                                myAdapter.notifyDataSetChanged();
                                xRecyclerView.refreshComplete();
                            }
                        });

                    }
                }).start();


            }

            @Override
            public void onLoadMore() {

            }
        });

        myAdapter = new MyAdapter(list);
        xRecyclerView.setAdapter(myAdapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list.set(CHANGEDPOSITION, "ondestroyed ~~");
    }
}
