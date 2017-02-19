package com.test.emptydemo.multiadapter;

import android.content.Context;

import com.test.emptydemo.bean.ChatMessage;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;


/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessage>


{

    private List<ChatMessage> datas;

    public ChatAdapterForRv(Context context, List<ChatMessage> datas) {
        super(context, datas);
        this.datas = datas;

        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
        addItemViewDelegate(new ViewpagerItemDelagate());
    }
//
//    @Override
//    public int getItemViewType(int position) {
//        ChatMessage chatMessage = datas.get(position);
//        if (chatMessage.getList() != null && chatMessage.getList().size() > 0) {
//            return 1;
//        } else if (chatMessage.isComMeg()) {
//            return 2;
//        } else {
//            return 3;
//        }
//    }
}
