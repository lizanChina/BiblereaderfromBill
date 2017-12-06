package com.bill.txtreader.bean;

import java.io.InputStream;

public class TxtFile {

    private InputStream fileStream;
    private String bookname;

    // 获取流
    public InputStream getFileStream() {
        return this.fileStream;
    }

    // 设置文件流
    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    // 获取书名
    public String getBookname() {
        return this.bookname;
    }

    // 设置书名
    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

}
