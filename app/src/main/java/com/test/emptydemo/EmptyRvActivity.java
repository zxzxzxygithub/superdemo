package com.test.emptydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;


/**
 * @author zhengyx
 * @date 2017/2/8
 */
public class EmptyRvActivity extends Activity {


    static ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emptyview);
        final XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.xrecyclerview);

        View emptyView = findViewById(R.id.text_empty);
        xRecyclerView.setEmptyView(emptyView);

        MyAdapter myAdapter = new MyAdapter(list);
        xRecyclerView.setAdapter(myAdapter);


    }


}
