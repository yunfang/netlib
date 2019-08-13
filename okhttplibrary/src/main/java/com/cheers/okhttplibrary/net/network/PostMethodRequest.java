package com.cheers.okhttplibrary.net.network;


import com.cheers.okhttplibrary.bean.BaseResult;

/**
 * Created by Administrator on 2016/6/23.
 */
public class PostMethodRequest<R extends BaseResult> extends Request<R> {


    public PostMethodRequest(Class<R> resultClass, String url) {
        super(Method.POST, resultClass, url);
    }
}
