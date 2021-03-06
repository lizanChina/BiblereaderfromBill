package com.bill.biblereaderfrombill.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bill.biblereaderfrombill.R;
import com.bill.biblereaderfrombill.menus.TxtBrightMenu;
import com.bill.biblereaderfrombill.menus.TxtProgressMenu;
import com.bill.biblereaderfrombill.menus.TxtStyleMenu;
import com.bill.biblereaderfrombill.menus.TxtTextMenu;
import com.bill.biblereaderfrombill.menus.TxtViewMenu;
import com.bill.common.Cons;
import com.bill.common.Logger;
import com.bill.txtreader.bean.TxtFile;
import com.bill.txtreader.bean.ITxtLoadListsner;
import com.bill.txtreader.bean.Txterror;
import com.bill.txtreader.mgr.IPageSeparateListener;
import com.bill.txtreader.mgr.ITxtManager;
import com.bill.txtreader.mgr.TxtManager;
import com.bill.txtreader.mgr.ITxtPageChangeListsner;
import com.bill.txtreader.mgr.TxtReadView;
import com.bill.txtreader.mgr.ITxtViewCenterAreaTouchListener;

import java.io.InputStream;

public class MainActivity extends Activity {

    private TxtReadView txtreadview;
    private ITxtManager txtManager;
    private TxtViewMenu mMenu;
    private TxtTextMenu mTxtTextMenu;
    private RelativeLayout mTitlebar;
    private TxtProgressMenu mProgressMenu;
    private TxtStyleMenu mStyleMenu;
    private TxtBrightMenu mBrightMenu;
    private RelativeLayout mLoadingView;
    private RelativeLayout mNodataView;
    private AnimationDrawable mAnimation;
    private TxtReadView mReadView;
    private TextView mTitle, mNodataText, mLoadingMsg;

    private int pageindex, pagenums;
    private Handler mHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d("MainActivity onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.txttest_activity_main);
        findview();
        initedata();
    }

    private void initedata() {
        mMenu = new TxtViewMenu(this);
        mTxtTextMenu = new TxtTextMenu(this, 30);
        mTitlebar.setVisibility(View.GONE);
        mStyleMenu = new TxtStyleMenu(this);
        mBrightMenu = new TxtBrightMenu(this);
        mProgressMenu = new TxtProgressMenu(this);

        TxtFile txtFile = new TxtFile();
        txtFile.setBookname(Cons.BOOK_NAME);
        showLoadingView(getResources().getString(R.string.loading));

        InputStream fileStream = null;
        try {
            fileStream = getAssets().open(Cons.FILE_NAME_BIBLE_ALL);
            if (fileStream != null) {
                Logger.d("fileStream len: " + fileStream.available());
            } else {
                Logger.e("load book failed");
                showNodataViewMsg("load book failed");
            }
        } catch (Exception e) {
            Logger.e("load book failed: " + e.toString());
            showNodataViewMsg("load book failed");
        }
        txtFile.setFileStream(fileStream);

        txtManager = new TxtManager(this, txtFile, new ITxtLoadListsner() {

            @Override
            public void onLoadSucess() {

                showdataView();
            }

            @Override
            public void onError(Txterror txterror) {

                showNodataViewMsg(txterror.message);
            }
        });

        txtreadview.setTxtManager(txtManager);
        registListener();
        startloadbook();

    }

    private void startloadbook() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                txtManager.LoadFile();
            }
        }).start();
    }

    private void findview() {
        txtreadview = (TxtReadView) findViewById(R.id.txtreadView);
        mTitlebar = (RelativeLayout) findViewById(R.id.textview_titlebar);
        mTitle = (TextView) findViewById(R.id.textview_title_textview);
        mLoadingView = (RelativeLayout) findViewById(R.id.txtview_loadingview);
        mNodataView = (RelativeLayout) findViewById(R.id.txtview_nodataview);
        mNodataText = (TextView) findViewById(R.id.txtview_nodata_text);
        mLoadingMsg = (TextView) findViewById(R.id.txtview_loading_msg);
        mTitle.setText(Cons.BOOK_NAME);

    }

    private void registListener() {

        txtreadview.setOnTxtPageChangeListener(new ITxtPageChangeListsner() {

            @Override
            public void onCurrentPage(int pageindex0, int pagenums0) {

                pageindex = pageindex0;
                pagenums = pagenums0;
                getHander().post(new Runnable() {

                    @Override
                    public void run() {

                        if (mProgressMenu != null) {
                            mProgressMenu.setPageIndex(pageindex, pagenums);
                        }
                    }
                });
            }
        });

        txtreadview.setOnPageSeparateListener(new IPageSeparateListener() {

            @Override
            public void onSeparateStart() {
                showProgressmenuLoadingview();
            }

            @Override
            public void onSeparateDone() {
                showProgressmenuDataview();
            }
        });

        txtreadview
                .setOnTxtViewCenterAreaTouchListener(new ITxtViewCenterAreaTouchListener() {

                    @Override
                    public void onOutSideAreaTouch() {
                        hidememu();

                    }

                    @Override
                    public void onAreaTouch() {
                        showMenu();

                    }
                });

        mLoadingView.setOnClickListener(new OnClickListener() {// 这个是用于获取到焦点，防止触摸事件继续传�?
            @Override
            public void onClick(View arg0) {

            }
        });

        mNodataView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                initedata();
            }
        });

        mMenu.setOnDismissListener(new OnDismissListener() {// 底部菜单消失监听

            @Override
            public void onDismiss() {
                getHander().post(new Runnable() {

                    @Override
                    public void run() {
                        mTitlebar.setVisibility(View.GONE);

                    }
                });

            }
        });

        mMenu.setOnTxtMenuClickListener(new TxtViewMenu.TxtMenuClockListener() {// 底部菜单选择监听

            @Override
            public void onTextMenuClicked() {

                showTextMenu();

            }

            @Override
            public void onStytleMenuClicked() {

                showStyleMune();

            }

            @Override
            public void onProgressMenuClicked() {

                showProgressMune();

            }

            @Override
            public void onLightMenuClicked() {

                showBrightMune();

            }

        });

        mTxtTextMenu.setonTxtTextChangeListener(new TxtTextMenu.onTxtTextChangeListener() {// 字体设置监听

            @Override
            public void onTextSizeChange(int spTextsize) {

                txtManager.getViewConfig().setTextSize(spTextsize);
                txtManager.CommitSetting();
                txtManager.jumptopage(1);
                txtManager.separatepage();

            }

            @Override
            public void onTextSortChange(String textsort) {
                txtManager.getViewConfig().setTextSort(textsort);
                txtManager.refreshBitmapText();

            }

        });

        mProgressMenu
                .setonProgressChangeListener(new TxtProgressMenu.onProgressChangeListener() {// 进度跳转

                    @Override
                    public void onProgressChange(int inpageindex) {

                        txtManager.jumptopage(inpageindex);

                    }
                });

        mStyleMenu.setonTxtStyleChangeListener(new TxtStyleMenu.onTxtStyleChangeListener() {

            @Override
            public void onStyleChange(int stylecolor) {
                if (stylecolor == TxtStyleMenu.STYLE_BLACK) {
                    txtManager.getViewConfig().setTextColor(Color.WHITE);
                } else {
                    txtManager.getViewConfig().setTextColor(Color.BLACK);
                }
                txtManager.CommitSetting();
                txtManager.getViewConfig().setBackBroundColor(stylecolor);
                txtManager.refreshBitmapBackground();
            }
        });

    }

    // ------------------------------------view---------------------------------------------------

    private void showProgressmenuLoadingview() {
        getHander().post(new Runnable() {

            @Override
            public void run() {
                if (mProgressMenu != null) {
                    mProgressMenu.showLoadingview();
                }
            }
        });

    }

    private void showProgressmenuDataview() {
        getHander().post(new Runnable() {

            @Override
            public void run() {

                if (mProgressMenu != null) {
                    mProgressMenu.setPageIndex(pageindex, pagenums);
                    mProgressMenu.showViewLayout();
                }
            }
        });
    }

    private void showMenu() {
        getHander().post(new Runnable() {

            @Override
            public void run() {

                View parent = MainActivity.this.getWindow().getDecorView();
                mMenu.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                mTitlebar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showTextMenu() {
        getHander().post(new Runnable() {

            @Override
            public void run() {

                mMenu.dismiss();
                View parent = MainActivity.this.getWindow().getDecorView();
                if (!mTxtTextMenu.isShowing())
                    mTxtTextMenu.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void showBrightMune() {
        getHander().post(new Runnable() {

            @Override
            public void run() {

                mMenu.dismiss();
                View parent = MainActivity.this.getWindow().getDecorView();
                mBrightMenu.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void showProgressMune() {
        getHander().post(new Runnable() {

            @Override
            public void run() {
                mMenu.dismiss();
                View parent = MainActivity.this.getWindow().getDecorView();
                mProgressMenu.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            }
        });

    }

    private void showStyleMune() {
        getHander().post(new Runnable() {

            @Override
            public void run() {
                mMenu.dismiss();
                View parent = MainActivity.this.getWindow().getDecorView();
                mStyleMenu.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void showLoadingView(final String msg) {

        getHander().post(new Runnable() {
            @Override
            public void run() {
                mLoadingMsg.setText(msg);
                mTitlebar.setVisibility(View.GONE);
                mNodataView.setVisibility(View.GONE);

                if (mAnimation == null) {
                    ImageView imageView = (ImageView) findViewById(R.id.txtview_loading_img);
                    imageView
                            .setBackgroundResource(R.drawable.loadingdialog_animation);
                    mAnimation = (AnimationDrawable) imageView.getBackground();
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            mAnimation.start();
                        }
                    });
                }
                mLoadingView.setVisibility(View.VISIBLE);

            }
        });

    }

    private void showdataView() {
        getHander().post(new Runnable() {

            @Override
            public void run() {

                mTitlebar.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mNodataView.setVisibility(View.GONE);

            }
        });

    }

    private void showNodataViewMsg(final String msg) {
        getHander().post(new Runnable() {
            @Override
            public void run() {
                mNodataText.setText(msg);
                mTitlebar.setVisibility(View.VISIBLE);
                mNodataView.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.GONE);

            }
        });
    }

    private void hidememu() {
        getHander().post(new Runnable() {
            @Override
            public void run() {
                mMenu.dismiss();

            }
        });
    }

    private Handler getHander() {
        if (mHander == null) {
            mHander = new Handler();
        }
        return mHander;
    }

    public void finishactivity(View view) {
        finish();
    }
}
