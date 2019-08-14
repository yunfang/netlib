package com.example.myapplication.base;

import com.cheers.okhttplibrary.global.LocalApplication;
import com.cheers.okhttplibrary.utils.LogUtils;
import com.example.myapplication.net.HttpConstants;

public class AppLocation extends LocalApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.PRINT_LOG = HttpConstants.PRINT_LOG;
    }
}
