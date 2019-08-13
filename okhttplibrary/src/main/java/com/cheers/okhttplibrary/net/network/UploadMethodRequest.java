package com.cheers.okhttplibrary.net.network;


import com.cheers.okhttplibrary.bean.BaseResult;

/**
 * Created by Administrator on 2016/6/23.
 */
public class UploadMethodRequest<R extends BaseResult> extends Request<R> {


    public UploadMethodRequest(Class<R> resultClass, String url) {
        super(Method.POST_UPLOAD, resultClass, url);
    }
}
