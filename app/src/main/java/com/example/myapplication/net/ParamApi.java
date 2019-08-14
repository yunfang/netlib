package com.example.myapplication.net;

import com.cheers.okhttplibrary.bean.BaseResult;
import com.cheers.okhttplibrary.net.network.PostMethodRequest;
import com.cheers.okhttplibrary.net.network.Request;

public class ParamApi {



    public final static Request<BaseResult> requestBaidu() {

        return new PostMethodRequest<BaseResult>(BaseResult.class, HttpConstants.HOST)
                .addArgument("username", "");
    }



    /**
     * 上次图片
     * @return
     */
    public final static Request<BaseResult> requestFileuploads(String file) {

        return new PostMethodRequest<BaseResult>(BaseResult.class, HttpConstants.HOST + HttpConstants.UPLOAD)
                .addArgument("filePath", file);
    }
}
