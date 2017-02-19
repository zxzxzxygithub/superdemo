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
public class HeaderFooterActivity extends Activity {


    static ArrayList<String> list = new ArrayList<>();

    static {
        for (int i = 0; i < 100; i++) {

            list.add("我是item a" + i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headerfooter);
        final XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.xrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        View header = LayoutInflater.from(this).inflate(R.layout.headerview, null);
        xRecyclerView.addHeaderView(header);
        header = LayoutInflater.from(this).inflate(R.layout.headerview, null);
        xRecyclerView.addHeaderView(header);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);

        MyAdapter myAdapter = new MyAdapter(list);
        xRecyclerView.setAdapter(myAdapter);


    }


}
