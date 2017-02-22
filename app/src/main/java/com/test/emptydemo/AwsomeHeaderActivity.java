package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;


/**
 * @author zhengyx
 * @date 2017/2/8
 */
public class AwsomeHeaderActivity extends Activity {


    static ArrayList<String> list = new ArrayList<>();

    static {
        for (int i = 0; i < 15; i++) {

            list.add("我是item a" + i);
        }
    }

    private PtrFrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awsomeheader);
        frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        initHouseHeader();


        final XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.home_lv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        View header = LayoutInflater.from(this).inflate(R.layout.headerview, null);
        xRecyclerView.addHeaderView(header);
        header = LayoutInflater.from(this).inflate(R.layout.headerview, null);
        xRecyclerView.addHeaderView(header);

        View footerView = LayoutInflater.from(this).inflate(R.layout.footerview, null);
        xRecyclerView.setFootView(footerView);
        footerView.setVisibility(View.VISIBLE);

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
//它的footer必须要设置loadinglistener才可显示出来
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

                Log.d("test", "onLoadMore: ");
            }
        });

        MyAdapter myAdapter = new MyAdapter(list);
        xRecyclerView.setAdapter(myAdapter);


    }

    private void initHouseHeader() {
        StoreHouseHeader header = new StoreHouseHeader(this);
        header.setPadding(0, 30, 0, 0);
        header.initWithString("DEMO");
        frame.setDurationToCloseHeader(3000);
        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);

        frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            }
        });
    }


}
