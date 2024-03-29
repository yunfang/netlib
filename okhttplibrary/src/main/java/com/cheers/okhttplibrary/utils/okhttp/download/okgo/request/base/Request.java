/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cheers.okhttplibrary.utils.okhttp.download.okgo.request.base;

import android.text.TextUtils;

import com.cheers.okhttplibrary.utils.okhttp.download.okgo.OkGo;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.adapter.Call;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.callback.Callback;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.convert.Converter;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.model.HttpHeaders;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.model.HttpMethod;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.model.HttpParams;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.utils.HttpUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/5
 * 描      述 ：
 * =======================================================================
 */
public abstract class Request<T, R extends Request> implements Serializable {
    private static final long serialVersionUID = -7174118653689916252L;

    protected String url;
    protected String baseUrl;
    protected transient OkHttpClient client;
    protected transient Object tag;
    protected int retryCount;
    protected String cacheKey;
    protected long cacheTime;                           //默认缓存的超时时间
    protected HttpParams params = new HttpParams();     //添加的param
    protected HttpHeaders headers = new HttpHeaders();  //添加的header

    protected transient okhttp3.Request mRequest;
    protected transient Call<T> call;
    protected transient Callback<T> callback;
    protected transient Converter<T> converter;
    protected transient ProgressRequestBody.UploadInterceptor uploadInterceptor;

    public Request(String url) {
        this.url = url;
        baseUrl = url;
        OkGo go = OkGo.getInstance();
        //默认添加 Accept-Language
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if (!TextUtils.isEmpty(acceptLanguage)) headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        //默认添加 User-Agent
        String userAgent = HttpHeaders.getUserAgent();
        if (!TextUtils.isEmpty(userAgent)) headers(HttpHeaders.HEAD_KEY_USER_AGENT, userAgent);
        //添加公共请求参数
        if (go.getCommonParams() != null) params(go.getCommonParams());
        if (go.getCommonHeaders() != null) headers(go.getCommonHeaders());
        //添加缓存模式
        retryCount = go.getRetryCount();
        cacheTime = go.getCacheTime();
    }

    @SuppressWarnings("unchecked")
    public R tag(Object tag) {
        this.tag = tag;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R retryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");
        this.retryCount = retryCount;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R client(OkHttpClient client) {
        HttpUtils.checkNotNull(client, "OkHttpClient == null");

        this.client = client;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R call(Call<T> call) {
        HttpUtils.checkNotNull(call, "call == null");

        this.call = call;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R converter(Converter<T> converter) {
        HttpUtils.checkNotNull(converter, "converter == null");

        this.converter = converter;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R cacheKey(String cacheKey) {
        HttpUtils.checkNotNull(cacheKey, "cacheKey == null");

        this.cacheKey = cacheKey;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R headers(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeHeader(String key) {
        headers.remove(key);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeAllHeaders() {
        headers.clear();
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(Map<String, String> params, boolean... isReplace) {
        this.params.put(params, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, String value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, int value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, float value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, double value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, long value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, char value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, boolean value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R addUrlParams(String key, List<String> values) {
        params.putUrlParams(key, values);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeParam(String key) {
        params.remove(key);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeAllParams() {
        params.clear();
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R uploadInterceptor(ProgressRequestBody.UploadInterceptor uploadInterceptor) {
        this.uploadInterceptor = uploadInterceptor;
        return (R) this;
    }

    /** 默认返回第一个参数 */
    public String getUrlParam(String key) {
        List<String> values = params.urlParamsMap.get(key);
        if (values != null && values.size() > 0) return values.get(0);
        return null;
    }

    /** 默认返回第一个参数 */
    public HttpParams.FileWrapper getFileParam(String key) {
        List<HttpParams.FileWrapper> values = params.fileParamsMap.get(key);
        if (values != null && values.size() > 0) return values.get(0);
        return null;
    }

    public HttpParams getParams() {
        return params;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Object getTag() {
        return tag;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public okhttp3.Request getRequest() {
        return mRequest;
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    public Converter<T> getConverter() {
        // converter 优先级高于 callback
        if (converter == null) converter = callback;
        HttpUtils.checkNotNull(converter, "converter == null, do you forget call Request#converter(Converter<T>) ?");
        return converter;
    }

    public abstract HttpMethod getMethod();

    /** 根据不同的请求方式和参数，生成不同的RequestBody */
    protected abstract RequestBody generateRequestBody();

    /** 根据不同的请求方式，将RequestBody转换成Request对象 */
    public abstract okhttp3.Request generateRequest(RequestBody requestBody);

    /** 获取okhttp的同步call对象 */
    public okhttp3.Call getRawCall() {
        //构建请求体，返回call对象
        RequestBody requestBody = generateRequestBody();
        if (requestBody != null) {
            ProgressRequestBody<T> progressRequestBody = new ProgressRequestBody<>(requestBody, callback);
            progressRequestBody.setInterceptor(uploadInterceptor);
            mRequest = generateRequest(progressRequestBody);
        } else {
            mRequest = generateRequest(null);
        }
        if (client == null) client = OkGo.getInstance().getOkHttpClient();
        return client.newCall(mRequest);
    }

    /** 普通调用，阻塞方法，同步请求执行 */
    public Response execute() throws IOException {
        return getRawCall().execute();
    }

}
