package com.bill.txtreader.mgr;

import android.graphics.Paint;

import com.bill.txtreader.bean.TxtFile;
import com.bill.txtreader.bean.ITxtLoadListsner;
import com.bill.txtreader.bean.Txterror;

/**
 * 这个主要是暴露给用户用来进行对txtreadview进行相应的设置的，包括字体、背景、进度跳转等等进行各种设置
 */
public interface ITxtManager {

    // 提交设置
    public void CommitSetting();

    // 获取配置
    public TxtReadViewConfig getViewConfig();

    // 获取字体大小
    public int getTextsize();

    // 获取字体画笔
    public Paint getTextPaint();

    // 获取指定页画笔
    public Paint getPageIndexTextPaint();

    // 获取控件宽度
    public int getViewWith();

    // 获取控件高度
    public int getViewHeigh();

    // 设置转换器
    public void setModeTransform(ITransformer t);

    // 获取行数
    public int getLinesNums();

    // 加载文件
    public void LoadFile();

    // 获取文件
    public TxtFile getTxtFile();

    // 跳转到指定页
    public void jumptopage(int pageindex);

    // 设置监听器
    public void setOnTxtLoadListsner(ITxtLoadListsner t);

    // 加载错误处理
    public void onTxtLoaderror(Txterror txterror);

    // 分开页
    public void separatepage();

    // 刷新bitmp文本
    public void refreshBitmapText();

    // 刷新bitmp背景
    public void refreshBitmapBackground();

}
