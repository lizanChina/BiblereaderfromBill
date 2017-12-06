package com.bill.txtreader.bean;

public interface ITxtLoadListsner {
    public void onLoadSucess();

    public void onError(Txterror txterror);
}
