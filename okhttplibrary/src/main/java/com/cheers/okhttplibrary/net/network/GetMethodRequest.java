package com.cheers.okhttplibrary.net.network;


import com.cheers.okhttplibrary.bean.BaseResult;

/**
 * Created by Administrator on 2016/6/23.
 */
public class GetMethodRequest<R extends BaseResult> extends Request<R> {


    public GetMethodRequest(Class<R> resultClass, String url) {
        super(Method.GET, resultClass, url);
    }

}
