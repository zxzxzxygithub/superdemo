package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;


/**
 * @author zhengyx
 * @date 2017/2/8
 */
public class LoadMoreRvActivity extends Activity {


    static ArrayList<String> list = new ArrayList<>();

    static {
        for (int i = 0; i < 20; i++) {

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
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(true);
//      设置loadingmore的style
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {




            }

            @Override
            public void onLoadMore() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list.add("113add ~~");
//                                很奇怪这行没有生效
//                                myAdapter.notifyItemChanged(changedPosition);
                                myAdapter.notifyDataSetChanged();
                                xRecyclerView.loadMoreComplete();
                            }
                        });

                    }
                }).start();

            }
        });

        myAdapter = new MyAdapter(list);
        xRecyclerView.setAdapter(myAdapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
