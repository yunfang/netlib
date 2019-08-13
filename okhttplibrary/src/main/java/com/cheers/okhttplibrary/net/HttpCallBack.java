package com.cheers.okhttplibrary.net;


import com.cheers.okhttplibrary.bean.BaseResult;

/**
 * Created by zhouyunfang on 17/6/1.
 */
public interface HttpCallBack<T extends BaseResult> {
    /**
     * 访问网络成功
     */
     void onSuccess(String json, T t);

    /**
     * 有错的类型请求
     */
    void onError(String json, T t);
}
