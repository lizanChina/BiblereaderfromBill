package com.bill.txtreader.mgr;

public interface IModeToViewTransform extends ITransformer {

    // 刷新view
    public void ReFreshView();

    // 加载第一页
    public void onloadfirstpage(Boolean islastpage);

    // 加载下一页
    public void onloadnextpage(Boolean islastpage);

    // 加载前一页
    public void onloadprepage(Boolean isfirstpage);

    // 加载指定页
    public void onloadpagefromindex(Boolean isfirstpage, Boolean islastpage);

    // 加载文件异常
    public void onloadFileException();

    // 无书籍
    public void onNoData();

    // 分页开始
    public void onPageSeparateStart();

    // 分页结束
    public void onPageSeparateDone();

}
