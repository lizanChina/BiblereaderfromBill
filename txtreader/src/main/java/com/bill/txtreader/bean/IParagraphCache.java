package com.bill.txtreader.bean;

public interface IParagraphCache {

    // 添加章节
    public void addParagraph(IParagraph p);

    // 清空
    public void Clear();

    // 通过索引获取章节
    public IParagraph getParagraphByIndex(int Paragraphindex);

    // 获取章节数目
    public int getParagraphSize();

    // 获取是否有章节缓存
    public Boolean isHasParagraphCache();

}
