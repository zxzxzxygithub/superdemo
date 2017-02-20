package com.test.emptydemo.bean;


import com.test.emptydemo.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage {
    private int icon;
    private String name;
    private String content;
    private String createDate;
    private boolean isComMeg;
    private int viewType;


    public final static int RECIEVE_MSG = 0;
    public final static int SEND_MSG = 1;
    private ArrayList<String> list;

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public ChatMessage(int icon, String name, String content,
                       String createDate, boolean isComMeg) {
        this.icon = icon;
        this.name = name;
        this.content = content;
        this.createDate = createDate;
        this.isComMeg = isComMeg;
    }

    public boolean isComMeg() {
        return isComMeg;
    }

    public void setComMeg(boolean isComMeg) {
        this.isComMeg = isComMeg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public String toString() {
        return "ChatMessage [icon=" + icon + ", name=" + name + ", content="
                + content + ", createDate=" + createDate + ", isComing = " + isComMeg() + "]";
    }

    public static List<ChatMessage> MOCK_DATAS = new ArrayList<>();

    static {
        ChatMessage msg = null;
        boolean notComeMsg = false;
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);

        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("item左右滑动1");
        list1.add("item左右滑动2");
        list1.add("item左右滑动3");
        msg.setList(list1);
        MOCK_DATAS.add(msg);
        boolean isComeMsg = true;
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);

        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                null, isComeMsg);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                null, notComeMsg);
        MOCK_DATAS.add(msg);
    }


}
