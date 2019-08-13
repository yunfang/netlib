package com.cheers.okhttplibrary.global;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;


/**
 * Created by pc on 2017/5/31.
 */

public class LocalApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LocalApplication.mContext = getApplicationContext();
//        initAppCrashHelper();

        initLogger();
    }

    private void initAppCrashHelper() {
        AppCrashHandler handler = AppCrashHandler.getInstance();
        handler.init(this);
    }


    private void initLogger() {
        //初始化日志打印器
        Logger.init("chreers_custom__");
    }


    public static final String DOWNLOAD_FILE_DIRECTORY = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "com.ssxg.cheers"
            + File.separator + "download" + File.separator;


    public static synchronized Context getContext() {
        return LocalApplication.mContext;
    }
}
