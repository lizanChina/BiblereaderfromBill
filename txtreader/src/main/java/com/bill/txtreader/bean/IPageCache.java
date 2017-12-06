package com.bill.txtreader.bean;

public interface IPageCache {

    // 添加页
    public void addPage(Page p);

    // 获取首页
    public Page getFirestPage();

    // 获取末页
    public Page getLastPage();

    // 获取指定页
    public Page getPage(int PageIndex);

    // 清空
    public void clear();

    // 获取页数
    public int getPagesize();

    // 获取最近元素
    public Page searClosestePage(CharElement element);

    // 是否有数据
    public Boolean isHasData();

}
