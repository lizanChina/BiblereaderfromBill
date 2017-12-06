package com.bill.txtreader.mgr;

public interface IManagerToModelTransform extends ITransformer {

    // 加载书籍
    public void loadTxtbook(ITransformer t);

    // 跳转到指定页
    public void jumptopage(int pageindex);

    // 分开页
    public void separatepage();

    // 刷新bitmap文本
    public void refreshbitmaptext();

    // 刷新bitmap背景
    public void refreshbitmapbackground();
}
