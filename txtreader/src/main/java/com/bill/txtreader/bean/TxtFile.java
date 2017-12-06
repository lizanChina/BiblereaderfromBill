package com.bill.txtreader.bean;

public class TxtFile {

    private String filepath;
    private String bookname;

    // 获取文件路径
    public String getFilepath() {
        return filepath;
    }

    // 设置文件路径
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    // 获取书名
    public String getBookname() {
        return bookname;
    }

    // 设置书名
    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

}
