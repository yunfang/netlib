package com.cheers.okhttplibrary.utils.cache;

import android.content.Context;

/**
 * Created by zhouyunfang on 2017/8/3.
 */

public class CacheUtilsObject {

    private static ObjectCache sObjectCache;

    /**
     * 初始化缓存
     *
     * @param context context
     */
    public static void init(Context context) {
        try {
            sObjectCache = ObjectCache.open(Config.OBJECT_CACHE_MEM_PERCENT, Config.getObjectCacheFolderPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("CacheUtils init error !");
        }
    }

    /**
     * 获取对象缓存实例
     *
     * @return 图片缓存实例
     */
    public static ObjectCache getObjectCache() {
        return sObjectCache;
    }

    /**
     * close图片缓存
     */
    public static void close() {

        if (sObjectCache != null) {
            sObjectCache.close();
        }
    }
}
