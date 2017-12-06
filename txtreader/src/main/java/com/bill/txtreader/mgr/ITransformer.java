package com.bill.txtreader.mgr;


import com.bill.txtreader.bean.Txterror;

public interface ITransformer {

    // 提交结果
    public void PostResult(Boolean t);

    // 提交错误
    public void PostError(Txterror txterror);

}

