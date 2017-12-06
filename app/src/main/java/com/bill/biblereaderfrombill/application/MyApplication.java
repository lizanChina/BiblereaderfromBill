package com.bill.biblereaderfrombill.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bill.common.Logger;

/**
 * Created by Bill on 2017/2/9.
 */

public class MyApplication extends Application {

    private static MyApplication application;

    @Override
    public void onCreate() {
        Logger.init(true, Log.VERBOSE, "Bill");
        super.onCreate();
        application = this;
    }

    public static Context getContext() {
        return application;
    }

}
