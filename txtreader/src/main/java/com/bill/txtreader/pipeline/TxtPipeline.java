package com.bill.txtreader.pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;

import com.bill.txtreader.bean.CharElement;
import com.bill.txtreader.bean.ILineChar;
import com.bill.txtreader.bean.LineChar;
import com.bill.txtreader.bean.Page;
import com.bill.txtreader.bean.IParagraph;
import com.bill.txtreader.bean.IParagraphCache;
import com.bill.txtreader.bean.ParagraphCache;
import com.bill.txtreader.bean.Paragraph;
import com.bill.txtreader.bean.TxtErrorCode;
import com.bill.txtreader.bean.Txterror;
import com.bill.txtreader.mgr.ITransformer;

public class TxtPipeline implements ITxtPipeline {

    private IParagraphCache IParagraphCache;

    public TxtPipeline() {
        IParagraphCache = new ParagraphCache();
    }

    @SuppressWarnings("resource")
    @Override
    public IParagraphCache LoadTxtFile(File txtfile, String txtcode, ITransformer t) {

        BufferedReader bufferedReader = null;
        Txterror txterror = new Txterror();

        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(txtfile), txtcode));
            } catch (FileNotFoundException e) {
                txterror.txterrorcode = TxtErrorCode.LOAD_BOOK_EXCEPTION;
                txterror.message = "无法找到该书籍文件";
                t.PostError(txterror);
                t.PostResult(false);

                return IParagraphCache;
            }
        } catch (UnsupportedEncodingException e) {

            txterror.txterrorcode = TxtErrorCode.LOAD_BOOK_EXCEPTION;
            txterror.message = "读取文件编码失败了";
            t.PostError(txterror);
            t.PostResult(false);
            return IParagraphCache;
        }

        String data = "";

        int i = 0;
        try {
            while ((data = bufferedReader.readLine()) != null) {
                IParagraph IParagraph = new Paragraph();
                IParagraph.setParagraphIndex(i);
                IParagraph.addStringdata(data);
                i++;
                IParagraphCache.addParagraph(IParagraph);
            }
        } catch (IOException e) {
            txterror.txterrorcode = TxtErrorCode.LOAD_BOOK_EXCEPTION;
            txterror.message = "加载书籍时出现io异常";
            t.PostError(txterror);
            t.PostResult(false);
            return IParagraphCache;
        }
        t.PostResult(true);

        return IParagraphCache;
    }

    /**
     * 思路就是一段一段的将段落数据转化为行数据，然后根据一页需要多少行进行填充 如果没有数据linesdata将会是空的
     *
     * @param startparagraphindex
     * @param startcharindex
     * @param paint
     * @param lineswidth
     * @param linesnums
     * @return 如果传入的开始位置超出了数据源本身，返回空，如果本身页面就没有数据，page不为null，数据为null
     */
    @Override
    public Page getPageFromPosition(int startparagraphindex, int startcharindex, Paint paint, float lineswidth,
                                    int linesnums) {

        Page page = new Page();// 将这个一页的开始位置标志
        page.firstElementCharindex = startcharindex;
        page.firstElementParagraphIndex = startparagraphindex;

        int pindex = startparagraphindex;// 将段落游标指向开始位置
        List<ILineChar> lines = new ArrayList<>();
        int countlinenums = 0;
        int psize = getParagraphCace().getParagraphSize();
        IParagraph IParagraph = getParagraphCace().getParagraphByIndex(startparagraphindex);
        if (IParagraph == null)// 开始段落位置超出了数据源
            return null;

        int length = IParagraph.getStringdata().length();
        Boolean istheend = startcharindex == length;
        // 如果游标开始的话，开始应该上一个位置的下一个字符，如果是段落的结尾应该换下段落，否则就是下个字符
        if (istheend) {
            startcharindex = 0;
        } else { // 字符串不为空才是下一个字符
            startcharindex++;
        }
        // 先处理掉最开始的段落，因为可能开始字符不是第一个
        List<ILineChar> s = getLinesFromParagraph(pindex, startcharindex, paint, lineswidth);
        lines.addAll(s);
        countlinenums = countlinenums + s.size();// 累加添加的行数

        startcharindex = 0;// 再吃标志开始字符为第一个
        pindex++;

        while (countlinenums < linesnums && pindex < psize) {// 一直填充直到超出页面行数
            s = getLinesFromParagraph(pindex, startcharindex, paint, lineswidth);
            lines.addAll(s);
            countlinenums = countlinenums + s.size();// 累加添加的行数
            pindex++;
        }

        if (lines.size() > 0) {
            // 添加的行数超出的话应该把超出的去掉
            if (lines.size() > linesnums) {
                while (lines.size() > linesnums) {
                    lines.remove(lines.size() - 1);
                }
            }

            // 获取最后一行，并标志最后字符位置
            ILineChar l = lines.get(lines.size() - 1);
            if (l.hasdata()) {// 也有可能最后一行并没有数据，没有数据就标志到下一段的开始位置
                page.lastElementCharindex = l.getLastElement().charindex;
                page.lastElementParagraphIndex = l.getLastElement().paragraphindex;

            } else {
                page.lastElementCharindex = 0;
                page.lastElementParagraphIndex = startparagraphindex + 1;

            }
            page.setLinesdata(lines);

        } else {// 没有数据
            page.lastElementCharindex = 0;
            page.lastElementParagraphIndex = startparagraphindex + 1;

        }

        return page;

    }

    @Override
    public List<ILineChar> getLinesFromParagraph(IParagraph p, int startcharindex, Paint paint, float measurewith) {
        List<ILineChar> linesdata = new ArrayList<ILineChar>();
        IParagraph IParagraph = p;
        Paint mPaint = paint;
        String str = IParagraph.getStringdata();
        int length = str.length();
        int charindex = startcharindex;
        int pindex = p.getIndex();

        if (length > 0 && length > startcharindex) {
            str = str.substring(startcharindex, length);

            while (str.length() > 0) {
                int nums = mPaint.breakText(str, true, measurewith, null);
                if (nums <= str.length()) {
                    ILineChar line = getLinesFromString(str.substring(0, nums), pindex, charindex);
                    linesdata.add(line);
                    str = str.substring(nums, str.length());
                    charindex = charindex + nums;
                } else {
                    ILineChar line = getLinesFromString(str, pindex, charindex);
                    linesdata.add(line);
                    str = "";
                }

            }

        }

        return linesdata;

    }

    @Override
    public List<ILineChar> getLinesFromParagraph(int paragraphindex, int startcharindex, Paint paint,
                                                 float measurewith) {
        return getLinesFromParagraph(IParagraphCache.getParagraphByIndex(paragraphindex), startcharindex, paint,
                measurewith);

    }

    @Override
    public ILineChar getLinesFromString(String data, int paragraphindex, int startcharindex) {
        ILineChar cl = new LineChar();
        char[] cs = data.toCharArray();
        int clength = cs.length;
        if (clength > 0) {
            for (int i = 0; i < clength; i++) {
                CharElement charElement = new CharElement();
                charElement.paragraphindex = paragraphindex;
                charElement.data = cs[i];
                charElement.charindex = startcharindex + i;
                cl.addElement(charElement);
            }
        }
        return cl;

    }

    @Override
    public IParagraphCache getParagraphCace() {

        return IParagraphCache;
    }

    @Override
    public Boolean HasCaChedata() {

        return IParagraphCache.isHasParagraphCache();
    }

}
