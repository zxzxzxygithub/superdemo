package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.test.emptydemo.bean.ChatMessage;
import com.test.emptydemo.multiadapter.ChatAdapterForRv;
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
        mDatas.addAll(ChatMessage.MOCK_DATAS);
        ChatAdapterForRv adapter = new ChatAdapterForRv(this, mDatas);

        xRecyclerView.setAdapter(adapter);


    }


}
