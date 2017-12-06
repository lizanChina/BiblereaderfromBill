package com.bill.txtreader.bean;


public class Paragraph implements IParagraph {
    private int index;
    private String data;

    @Override
    public void setParagraphIndex(int index) {
        this.index = index;
    }

    @Override
    public void Clear() {
    }

    @Override
    public void addStringdata(String str) {
        data = str;
    }

    @Override
    public int getIndex() {

        return index;
    }

    @Override
    public String getStringdata() {

        return data;
    }


}
