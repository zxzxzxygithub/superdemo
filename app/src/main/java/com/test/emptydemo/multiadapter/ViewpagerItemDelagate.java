package com.test.emptydemo.multiadapter;

import android.content.Context;
import android.view.View;

import com.test.emptydemo.R;
import com.test.emptydemo.bean.ChatMessage;
import com.test.emptydemo.bean.ViewPagerData;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class ViewpagerItemDelagate implements ItemViewDelegate<ViewPagerData>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.complexone;
    }

    @Override
    public boolean isForViewType(ViewPagerData item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, ViewPagerData chatMessage, int position)
    {
//        holder.setText(R.id.chat_from_content, chatMessage.getContent());
//        holder.setText(R.id.chat_from_name, chatMessage.getName());
//        holder.setImageResource(R.id.chat_from_icon, chatMessage.getIcon());
    }


    public  class ViewPagerHolder extends  ViewHolder{
        public ViewPagerHolder(Context context, View itemView) {
            super(context, itemView);
        }
    }




}
