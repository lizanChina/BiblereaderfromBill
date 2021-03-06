package com.bill.biblereaderfrombill.menus;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bill.biblereaderfrombill.R;

public class TxtStyleMenu extends PopupWindow {


    public static final int STYLE_BLACK = R.drawable.reading__reading_themes_vine_dark;
    public static final int STYLE_GRAY = R.drawable.reading__reading_themes_vine_green;
    public static final int STYLE_BULE = R.drawable.reading__reading_themes_vine_white;


    private Context mContext;
    private int mWindow_With;
    private int mWindow_Heigh;
    private int mSelectedposition;
    private View SelectedTag;
    private onTxtStyleChangeListener mListener;

    public TxtStyleMenu(Context context) {
        this.mContext = context;
        inite();
    }

    public void setonTxtStyleChangeListener(onTxtStyleChangeListener listener) {
        mListener = listener;
    }

    @SuppressLint({"NewApi", "CutPasteId"})
    private void inite() {
        WindowManager m = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(metrics);

        mWindow_With = metrics.widthPixels;
        mWindow_Heigh = metrics.heightPixels;

        int rootwith = mWindow_With;
        int rootheigh = mWindow_Heigh / 7;

        LinearLayout layout = (LinearLayout) LinearLayout.inflate(mContext, R.layout.txtstylemenu_layout, null);

        this.setWidth(rootwith);
        this.setHeight(rootheigh);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setContentView(layout);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#88000000"));


        RelativeLayout s_layout1 = (RelativeLayout) layout.findViewById(R.id.txtstyle1_layout);
        RelativeLayout s_layout2 = (RelativeLayout) layout.findViewById(R.id.txtstyle2_layout);
        RelativeLayout s_layout3 = (RelativeLayout) layout.findViewById(R.id.txtstyle3_layout);


        ImageView view1 = (ImageView) layout.findViewById(R.id.txtstyle1);
        ImageView view2 = (ImageView) layout.findViewById(R.id.txtstyle2);
        ImageView view3 = (ImageView) layout.findViewById(R.id.txtstyle3);


        final View slid1 = layout.findViewById(R.id.txtstyle1_tag);
        final View slid2 = layout.findViewById(R.id.txtstyle2_tag);
        final View slid3 = layout.findViewById(R.id.txtstyle3_tag);


        view1.setBackgroundResource(STYLE_BLACK);
        view2.setBackgroundResource(STYLE_GRAY);
        view3.setBackgroundResource(STYLE_BULE);


        mSelectedposition = 1;
        SelectedTag = slid1;

        s_layout1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (mSelectedposition != 1) {
                    mListener.onStyleChange(STYLE_BLACK);
                    hideSlidtag(SelectedTag);
                    SelectedTag = slid1;
                    mSelectedposition = 1;
                    showSlidTag(SelectedTag);
                }

            }
        });

        s_layout2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mSelectedposition != 2) {
                    mListener.onStyleChange(STYLE_GRAY);
                    hideSlidtag(SelectedTag);
                    SelectedTag = slid2;
                    mSelectedposition = 2;
                    showSlidTag(SelectedTag);
                }

            }
        });

        s_layout3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mSelectedposition != 3) {
                    mListener.onStyleChange(STYLE_BULE);
                    hideSlidtag(SelectedTag);
                    SelectedTag = slid3;
                    mSelectedposition = 3;
                    showSlidTag(SelectedTag);
                }

            }
        });


        this.setBackgroundDrawable(dw);

    }

    private void hideSlidtag(View mSelectedTag) {
        mSelectedTag.setVisibility(View.INVISIBLE);
    }

    private void showSlidTag(View mSelectedTag) {
        mSelectedTag.setVisibility(View.VISIBLE);

    }

    public interface onTxtStyleChangeListener {
        public void onStyleChange(int stylecolor);

    }

}
