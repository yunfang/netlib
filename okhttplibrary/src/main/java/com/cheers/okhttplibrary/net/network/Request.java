package com.cheers.okhttplibrary.net.network;

import com.cheers.okhttplibrary.bean.BaseResult;
import com.cheers.okhttplibrary.global.LocalApplication;
import com.cheers.okhttplibrary.net.HttpCallBack;
import com.cheers.okhttplibrary.net.HttpHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/8
 * 描      述 ：
 * =======================================================================
 */

public class Request<R extends BaseResult> {

    private Class<R> mResultClass;
    private HashMap<String, Object> mArguments = new HashMap<String, Object>();
    private String mUrl;
    private int mMethodType = 0;//0为GET的方式请求  ，1为POST的方式请求

    public Request(int methodType, Class<R> resultClass, String url) {
        mResultClass = resultClass;
        mUrl = url;
        mMethodType = methodType;
    }

    /**
     * 异步执行API
     *
     * @param requestCallback 执行完毕后的回调
     */
    public void execute(RequestCallback<R> requestCallback) {
        performRequest(requestCallback);
    }

    private void performRequest(RequestCallback<R> requestCallback) {

//        String UrlParam = getUrl();

        switch (mMethodType){
            case Method.GET://0为GET的方式请求
                gethttp(requestCallback,Method.GET,null);
                break;
            case Method.POST://1为POST的方式请求
                gethttp(requestCallback,Method.POST,null);
                break;
        }
    }
    /**
     * 异步执行API
     *
     * @param requestCallback 执行完毕后的回调
     */
    public void uploadexecute(RequestCallback<R> requestCallback,File file,String fileParamName) {
        uploadperformRequest(requestCallback,file,fileParamName);
    }

    private void uploadperformRequest(RequestCallback<R> requestCallback,File file,String upload_fileParamName) {

//        String UrlParam = getUrl();
         gethttp(requestCallback,Method.POST_UPLOAD,file,upload_fileParamName);

    }
    private void gethttp(final RequestCallback<R> mRequestCallback,int mMethod,File file,String... upload_fileParamName) {
        switch (mMethod){
            case Method.GET:
                HttpHelper.doGetRequest(LocalApplication.getContext(), mUrl, mArguments, mResultClass, new HttpCallBack<R>() {
                    @Override
                    public void onSuccess(String json, R r) {
                        mRequestCallback.onRequestSuccess(r,json);
                    }

                    @Override
                    public void onError(String json, R r) {
                        mRequestCallback.onRequestFailure(json,null);
                    }
                });
                break;

            case Method.POST:
                HttpHelper.doPostRequest(LocalApplication.getContext(), mUrl, mArguments, mResultClass, new HttpCallBack<R>() {
                    @Override
                    public void onSuccess(String json, R r) {
                        mRequestCallback.onRequestSuccess(r,json);
                    }

                    @Override
                    public void onError(String json, R r) {
                        mRequestCallback.onRequestFailure(json,null);
                    }
                });
                break;
            case Method.POST_UPLOAD:
                HttpHelper.UploadRequest(LocalApplication.getContext(), mUrl,file, mArguments, mResultClass,upload_fileParamName, new HttpCallBack<R>() {
                    @Override
                    public void onSuccess(String json, R r) {
                        mRequestCallback.onRequestSuccess(r,json);
                    }

                    @Override
                    public void onError(String json, R r) {
                        mRequestCallback.onRequestFailure(json,null);
                    }
                });
                break;
        }
    }


    private String getUrl() {
        StringBuffer buffer = new StringBuffer(mUrl);
        Iterator<Map.Entry<String, Object>> iter = mArguments.entrySet()
                .iterator();
        while (iter.hasNext()) {

            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
            buffer.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        return buffer.toString().replaceFirst("&","");
    }


    /**
     *
     * @param key      请求参数key
     * @param argument 请求参数value
     * @return 对象自身
     */
    public Request<R> addArgument(String key, Object argument) {
        if (!isObjectToStringNull(argument)) {
            mArguments.put(key, argument.toString());
        }
        return this;
    }



    private boolean isObjectToStringNull(Object object) {
//        return object == null || TextUtils.isEmpty(object.toString());
        return object == null;
    }



}
