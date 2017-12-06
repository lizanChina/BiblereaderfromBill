package com.bill.txtreader.pipeline;

import java.io.InputStream;
import java.util.List;

import android.graphics.Paint;

import com.bill.txtreader.bean.ILineChar;
import com.bill.txtreader.bean.Page;
import com.bill.txtreader.bean.IParagraph;
import com.bill.txtreader.bean.IParagraphCache;
import com.bill.txtreader.mgr.ITransformer;


public interface ITxtPipeline {

    /**
     * 加载txt文件数据，返回段落集合缓存
     *
     * @param txtfile
     * @return
     */
    public IParagraphCache loadTxtFile(InputStream txtfile, String charsetname, ITransformer t);

    /**
     * 思路就是一段一段的将段落数据转化为行数据，然后根据一页需要多少行进行填充
     * 如果没有数据linesdata将会是空的
     *
     * @param startparagraphindex
     * @param startcharindex
     * @param paint
     * @param linesnums
     * @return 如果传入的开始位置超出了数据源本身，返回空，如果本身页面就没有数据，page不为null，数据为null
     */
    public Page getPageFromPosition(int startparagraphindex, int startcharindex, Paint paint, float lineswith, int linesnums);

    /**
     * 将段落数据转化为行集合
     *
     * @param p
     * @param startcharindex 开始位置，因为可能不是从段落的最开始开始转化
     * @return 不会返回null
     */
    public List<ILineChar> getLinesFromParagraph(IParagraph p, int startcharindex, Paint paint, float measurewith);

    public List<ILineChar> getLinesFromParagraph(int paragraphindex, int startcharindex, Paint paint, float measurewith) throws ArrayIndexOutOfBoundsException;

    /**
     * 将字符串转化为一行
     *
     * @param data
     * @param startcharindex 该字符串最开始的字符你想要标志它在段落的开始位置
     * @return 不会返回null
     */
    public ILineChar getLinesFromString(String data, int paragraphindex, int startcharindex);

    public IParagraphCache getParagraphCace();

    /**
     * 判断是否有缓存，使用这个类的时候建议调用这个方法判断一下，以免缓存清除出现问题
     *
     * @return
     */
    public Boolean HasCaChedata();


}
