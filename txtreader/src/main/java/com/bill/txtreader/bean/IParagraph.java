package com.bill.txtreader.bean;

public interface IParagraph {

    // 设置章节号
    void setParagraphIndex(int index);

    // 清空
    void Clear();

    // 添加文本字符串
    void addStringdata(String str);

    // 获取本字符串
    String getStringdata();

    // 获取索引
    int getIndex();

}
