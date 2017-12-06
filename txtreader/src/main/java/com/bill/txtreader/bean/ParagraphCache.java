package com.bill.txtreader.bean;

import java.util.ArrayList;
import java.util.List;
import com.bill.common.Logger;

public class ParagraphCache implements IParagraphCache {

    private List<IParagraph> paragraphs;

    public ParagraphCache() {
        paragraphs = new ArrayList<>();
    }

    @Override
    public void addParagraph(IParagraph p) {
        paragraphs.add(p);
    }

    @Override
    public void Clear() {
        paragraphs.clear();
        System.gc();
        System.gc();
    }

    @Override
    public IParagraph getParagraphByIndex(int Paragraphindex) {


        if (Paragraphindex >= 0 && Paragraphindex < paragraphs.size()) {
            return paragraphs.get(Paragraphindex);
        }
        Logger.d("Paragraphindex:" + Paragraphindex);
        Logger.d("paragraphs.size():" + paragraphs.size());
        return null;
    }

    @Override
    public int getParagraphSize() {

        return paragraphs.size();
    }

    @Override
    public Boolean isHasParagraphCache() {

        return getParagraphSize() != 0;
    }

}
