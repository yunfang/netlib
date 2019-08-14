package com.cheers.okhttplibrary.global;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.cheers.okhttplibrary.utils.OkHttpUtils;
import com.cheers.okhttplibrary.utils.cache.CacheUtilsObject;
import com.cheers.okhttplibrary.utils.cache.Config;
import com.orhanobut.logger.Logger;

import java.io.File;


/**
 * Created by zhouyunfang on 2017/5/31.
 */

public class LocalApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LocalApplication.mContext = getApplicationContext();
//        initAppCrashHelper();

        initLogger();

        DOWNLOAD_FILE_DIRECTORY = Environment
                .getExternalStorageDirectory()
                + File.separator
                + getContext().getPackageName()
                + File.separator + "download" + File.separator;
        System.out.println("DOWNLOAD_FILE_DIRECTORY----->"+DOWNLOAD_FILE_DIRECTORY);

        Config.init(this);
        CacheUtilsObject.init(this);
        OkHttpUtils.initOkGo(this);//下载初始化
    }

    private void initAppCrashHelper() {
        AppCrashHandler handler = AppCrashHandler.getInstance();
        handler.init(this);
    }


    private void initLogger() {
        //初始化日志打印器
        Logger.init("chreers_custom__");
    }


    public static String DOWNLOAD_FILE_DIRECTORY = "" ;

    public static synchronized Context getContext() {
        return LocalApplication.mContext;
    }
}
