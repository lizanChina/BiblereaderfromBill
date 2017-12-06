package com.bill.txtreader.bean;

public class CharElement {

    // 字符
    public char data;
    // 居中索引
    public int paragraphindex;
    // 字符索引
    public int charindex;
    // X坐标
    public float Xposition;
    // Y坐标
    public float Yposition;

    // 是否为空格
    public Boolean isSpace() {
        return Character.isWhitespace(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CharElement) {

            CharElement c = (CharElement) obj;
            return data == c.data && paragraphindex == c.charindex
                    && charindex == c.charindex;
        }
        return false;
    }

}
