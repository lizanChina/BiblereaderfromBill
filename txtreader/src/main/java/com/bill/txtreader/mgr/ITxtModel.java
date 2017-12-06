package com.bill.txtreader.mgr;

import com.bill.txtreader.bean.Page;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 主要是相关数据处理
 */
public interface ITxtModel {

    /**
     * 去数据库中查找已经分页好的数据
     *
     * @param pageindex 下标是从1开始
     * @return 如果没有查找到的画返回的是空
     */
    public Page getPage(int pageindex);

    /**
     * 必须分页完成后执行这个才有效果，这个传入是页码不能小于1，也不能超出分页后的页码总数
     *
     * @param pageindex
     */
    public void loadpage(int pageindex);

    /**
     * 从开始位置填充pre mid next页，没有数据的页都是null
     *
     * @param paragraphindex
     * @param charindex
     * @param markpageindex
     */
    public void loadFromChar(int paragraphindex, int charindex,
                             int markpageindex);

    public void loadFirstPage();

    public void loadnextpage();

    public void loadprepage();

    public TxtBitmapCache getBitmapCache();

    public void setModeToViewTransform(ITransformer t);

    /**
     * 启动线程进行分页处理
     */
    public void separatebooktopages();

    /**
     * 返回看是否获取数据成功
     *
     * @param t
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void LoadTxtFile(ITransformer t) throws FileNotFoundException,
            IOException;

    public Page getPrePage();

    public Page getMidPage();

    public Page getNextPage();

    public int getPageNums();

    public Boolean issaparatedone();
}
