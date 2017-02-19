package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;


/**
 * @author zhengyx
 * @date 2017/2/8
 */
public class CommonAdapterActivity extends Activity {


    static ArrayList<String> list = new ArrayList<>();

    static {
        for (int i = 0; i < 30; i++) {

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

        CommonAdapter<String> mAdapter = new CommonAdapter<String>(this, R.layout.item, list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.text, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
            }
        };

        xRecyclerView.setAdapter(mAdapter);


    }


}
