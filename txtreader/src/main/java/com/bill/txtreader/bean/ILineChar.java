package com.bill.txtreader.bean;

public interface ILineChar {

    // 获取元素个数
    public int getElementSize();

    // 添加元素
    public void addElement(CharElement charElement);

    // 获取首个元素
    public CharElement getFirstElement();

    // 获取行文本字符串
    public String getLineString();

    // 获取行文本字符集
    public char[] getLineChars();

    // 清空
    public void clear();

    // 是否包含数据
    public Boolean hasdata();

    // 获取末尾元素
    public CharElement getLastElement();

}
