package com.bill.txtreader.bean;

import java.util.ArrayList;
import java.util.List;

public class Page {
    // 首元素字符索引
    public int firstElementCharindex;
    // 首元素字符居中索引
    public int firstElementParagraphIndex;
    // 末元素字符索引
    public int lastElementCharindex;
    // 未元素字符居中索引
    public int lastElementParagraphIndex;
    // 页码索引
    public int pageindex;
    // 是否为首页
    private boolean istheFirstpage = false;
    // 是否为末页
    private Boolean isTheLsatPage = false;
    // 页数据
    private List<ILineChar> linesdata;

    public Page() {
        init();
    }

    public Page(int pageindex) {
        this.pageindex = pageindex;
        init();
    }

    public int getPageindex() {
        return pageindex;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    private void init() {

        linesdata = new ArrayList<>();
    }

    public boolean isIstheFirstpage() {
        return istheFirstpage;
    }

    public void setIstheFirstpage(boolean istheFirstpage) {
        this.istheFirstpage = istheFirstpage;
    }

    public Boolean getIsTheLsatPage() {
        return isTheLsatPage;
    }

    public void setIsTheLsatPage(Boolean isTheLsatPage) {
        this.isTheLsatPage = isTheLsatPage;
    }

    public void addLine(ILineChar ILineChar) {
        linesdata.add(ILineChar);

    }

    public void clearLine() {
        linesdata.clear();
    }

    public List<ILineChar> getLinesdata() {
        return linesdata;
    }

    public int getLinesSize() {
        return linesdata.size();
    }

    public Boolean HasData() {
        return getLinesSize() != 0;
    }

    public String getPageString() {
        String str = "";
        for (ILineChar l : linesdata) {
            str = str + l.getLineString();
        }
        return str;
    }

    public void setLinesdata(List<ILineChar> linesdata) {
        this.linesdata = linesdata;
    }

}
