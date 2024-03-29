package com.cheers.okhttplibrary.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.cheers.okhttplibrary.bean.BaseResult;
import com.cheers.okhttplibrary.utils.LogUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *   Created by zhouyunfang on 17/6/1.
 */
public class HttpHelper {
    private static Gson mGson = new Gson();
    private volatile static OkHttpClient instance; //声明成 volatile
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致

    private static int TYPE_GET = 0;//get请求
    private static int TYPE_POST = 1;//post请求
    private static int TYPE_POST_JSON = 2;//post的json请求
    private static int TYPE_UPLOAD = 3;//post的json请求
    private static SSLContext sslContext;


    public static <T extends BaseResult> void doGetRequest(Context context, final String url, final Map<String,
            Object> map, final Class<T> clazz, final HttpCallBack<T> httpCallBack) {
//        if (!NetUtils.hasNetwork(context)) {
//            ToastUtils.showToast("网络连接不可用");
//            return;
//        }
        final String json =getJson(map);
        Observable.just(url)
                .map(new Func1<String, Map>() {
                    @Override
                    public Map call(String url) {
                        return getReponse(url, json,map,TYPE_GET);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Map>() {
                    @Override
                    public void call(Map response) {
                        doResult(response, httpCallBack, clazz, url, json);
                    }
                });
    }

    public static <T extends BaseResult> void doPostRequest(Context context, final String url, final Map<String,
            Object> map, final Class<T> clazz, final HttpCallBack<T> httpCallBack) {
//        if (!NetUtils.hasNetwork(context)) {
//            ToastUtils.showToast("网络连接不可用");
//            return;
//        }
        final String json =getJson(map);
        Observable.just(url)
                .map(new Func1<String, Map>() {
                    @Override
                    public Map call(String url) {
                        return getReponse(url, json,map,TYPE_POST);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Map>() {
                    @Override
                    public void call(Map response) {
                        doResult(response, httpCallBack, clazz, url, json);
                    }
                });
    }

    public static <T extends BaseResult> void UploadRequest(Context context, final String url, final File file, final Map<String,
            Object> map, final Class<T> clazz, final String[] upload_fileParamName, final HttpCallBack<T> httpCallBack) {
//        if (!NetUtils.hasNetwork(context)) {
//            ToastUtils.showToast("网络连接不可用");
//            return;
//        }
        final String json =getJson(map);
        Observable.just(url)
                .map(new Func1<String, Map>() {
                    @Override
                    public Map call(String url) {
                        return getUploadReponse(url, json,map,file,upload_fileParamName);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Map>() {
                    @Override
                    public void call(Map response) {
                        doResult(response, httpCallBack, clazz, url, json);
                    }
                });
    }

    /**
     * Android okHttp post请求参数是json格式的方法
     * @param context  上下文
     * @param url      请求地址
     * @param map      请求参数的map
     * @param clazz    解析的bean类
     * @param httpCallBack  回调方法
     * @param <T>
     */
    public static <T extends BaseResult> void doBodyPost(Context context, final String url, final Map<String,
            Object> map, final Class<T> clazz, final HttpCallBack<T> httpCallBack) {
//        if (!NetUtils.hasNetwork(context)) {
//            ToastUtils.showToast("网络连接不可用");
//            return;
//        }
        final String requestjson =getJson(map);
        Observable.just(url)
                .map(new Func1<String, Map>() {
                    @Override
                    public Map call(String url) {
                        return getReponse(url, requestjson,map,TYPE_POST_JSON);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Map>() {
                    @Override
                    public void call(Map response) {
                        doResult(response, httpCallBack, clazz, url, requestjson);
                    }
                });
    }

    private static <T extends BaseResult> void doResult(Map response, HttpCallBack<T> httpCallBack, Class<T> clazz, String url, String requestjson) {

        if (response != null) {
            if (!TextUtils.isEmpty(String.valueOf(response.get("body")))) {
                String result = response.get("body").toString();
                systemLog((int)response.get("requestType"),url,(String)response.get("url"),result,requestjson);
//                T t = mGson.fromJson(result, clazz);
                if(TextUtils.equals(String.valueOf(response.get("code")),"200")){
                    try {
                        T t = mGson.fromJson(result, clazz);
                        httpCallBack.onSuccess(result, mGson.fromJson(result, clazz));
                    }catch (Exception e){
                        e.printStackTrace();
                        httpCallBack.onError(result, null);
                    }

                }else{
                    httpCallBack.onError(result, null);
                }
            } else {
                if(TextUtils.equals(String.valueOf(response.get("code")),"200")){
                    httpCallBack.onSuccess(null, null);
                }else{
                    if(response.get("message") != null){
                        httpCallBack.onError(TextUtils.isEmpty(response.get("message").toString()) ? response.get("body").toString() : response.get("message").toString(),null);
                        systemLog((int)response.get("requestType"),url,(String)response.get("url"),(TextUtils.isEmpty(response.get("message").toString()) ? response.get("body").toString() : response.get("message").toString()),requestjson);
                    }else{
                        httpCallBack.onError(response.get("code")+"",null);
                        systemLog((int)response.get("requestType"),url,(String)response.get("url"),response.get("code")+"     "+response.get("body").toString(),requestjson);
                    }

                }
            }
        }

    }

    //请求网络
    private static Map getReponse(String url, String json, Map<String,Object> paramsMap, int requestType) {
        StringBuilder tempParams = null;
        RequestBody body;
        Map<String, Object> responsemap = new HashMap<String, Object>();

        Request request = null;
        switch (requestType){
            case 0://get请求
                tempParams = maptoParam(paramsMap);
                request = new Request.Builder().tag(url).url(String.format("%s?%s",url, tempParams.toString())).build();
                break;
            case 1://post请求
                tempParams = maptoParam(paramsMap);
                String params = tempParams.toString();
                body = RequestBody.create(MEDIA_TYPE_JSON, params);
                request = new Request.Builder().tag(url).url(url).post(body).build();
                break;
            case 2://POST_JSON
                body = RequestBody.create(JSON, json);
                request = new Request.Builder().url(url)
                        .post(body).tag(url)
                        .build();
                break;
        }

        Response response = null;
        try {
            response = getInstance().newCall(request).execute();
            responsemap.put("code",response.code());
            responsemap.put("body", response.body().string());
            responsemap.put("requestType",requestType);//请求的类型
            if(requestType != 2){
                responsemap.put("url",String.format("%s?%s",url, tempParams.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            responsemap.put("body", "");
            responsemap.put("requestType",requestType);//请求的类型
            if(requestType != 2){
                responsemap.put("url",String.format("%s?%s",url, tempParams.toString()));
            }
            if (e instanceof SocketTimeoutException) {
                responsemap.put("message", "网络连接超时,请检查网络！");
            } else if (e instanceof SocketException) {
                if (e instanceof ConnectException) {
                    responsemap.put("message", "网络未连接，请检查网络！");
                } else {
                    responsemap.put("message", "网络错误，请检查网络！");
                }
            }else if(e instanceof UnknownHostException){
                responsemap.put("message", "网络异常,请检查网络!");
            }else{
                responsemap.put("message", "服务器无响应，请稍后重试！");
            }
        }
        return responsemap;
    }

    //请求网络
    private static Map getUploadReponse(String url, String json, Map<String, Object> paramsMap, File file, String[] upload_fileParamName) {
        StringBuilder tempParams = null;
        RequestBody body;
        Map<String, Object> responsemap = new HashMap<String, Object>();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Builder requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if(upload_fileParamName != null && upload_fileParamName.length > 0){
            requestBody.addFormDataPart(upload_fileParamName[0], System.currentTimeMillis()+".png", fileBody);
        }else{
            requestBody.addFormDataPart("headImage", System.currentTimeMillis()+".png", fileBody);
        }
        for (Map.Entry entry : paramsMap.entrySet()) {
            requestBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        MultipartBody build = requestBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(build)
                .build();

        Response response = null;
        try {
            response = getInstance().newCall(request).execute();
            responsemap.put("code",response.code());
            responsemap.put("body", response.body().string());
            responsemap.put("requestType",TYPE_UPLOAD);//请求的类型
//            if(TYPE_UPLOAD != 2){
//                responsemap.put("url",String.format("%s?%s",url, tempParams.toString()));
//            }
        } catch (Exception e) {
            e.printStackTrace();
            responsemap.put("body", "");
            responsemap.put("requestType",TYPE_UPLOAD);//请求的类型
//            if(TYPE_UPLOAD != 2){
//                responsemap.put("url",String.format("%s?%s",url, tempParams.toString()));
//            }
            if (e instanceof SocketTimeoutException) {
                responsemap.put("message", "网络连接超时,请检查网络！");
            } else if (e instanceof SocketException) {
                if (e instanceof ConnectException) {
                    responsemap.put("message", "网络未连接，请检查网络！");
                } else {
                    responsemap.put("message", "网络错误，请检查网络！");
                }
            }else if(e instanceof UnknownHostException){
                responsemap.put("message", "网络异常,请检查网络!");
            }else{
                responsemap.put("message", "服务器无响应，请稍后重试！");
            }
        }
        return responsemap;
    }

    //初始化OkHttpClient
    private static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (OkHttpClient.class) {
                if (instance == null) {
                    instance = new OkHttpClient.Builder()
                            .connectTimeout(40, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(new LoggingInterceptor())
                            .certificatePinner(new CertificatePinner
                                    .Builder()
                                    .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=")
                                    .build())
                            .build();
                }
            }
        }
        return instance;
    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
//            LogUtils.logI("header：", request.url() +"\n"+request.headers());
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            return response;
        }
    }

    //转换拼接参数
    private static StringBuilder maptoParam(Map<String,Object> paramsMap){
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            try {
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(String.valueOf(paramsMap.get(key)), "utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                LogUtils.logI("参数异常");
            }
            pos++;
        }

        return tempParams;
    }

    private static void systemLog(int requestTyep, String host, String url_param, String result, String json){
        switch (requestTyep){
            case 0:
                LogUtils.logI("GET请求:", url_param + "\n" + json);
                LogUtils.logI("GET返回: ", result);
                break;
            case 1:
                LogUtils.logI("POST请求:", url_param + "\n" + json);
                LogUtils.logI("POST返回: ", result);
                break;
            case 2:
                LogUtils.logI("POST请求:", host + "\n" + json);
                LogUtils.logI("POST返回: ", result);
                break;
        }


    }

    /**
     * 取消某个请求
     *
     * @param tag
     */
    public static void cancelTag(Object tag) {
        for (Call call : getInstance().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getInstance().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

    }

    /**
     * 取消所有请求
     */
    public static void cancelAllTag() {
        getInstance().dispatcher().cancelAll();
    }


    //生成JSONObject 对象
    private static void params2Json(final JSONObject jsonObject, final List<KeyValue> paramList) throws JSONException {
        HashSet<String> arraySet = new HashSet<String>(paramList.size());
        LinkedHashMap<String, JSONArray> tempData = new LinkedHashMap<String, JSONArray>(paramList.size());
        for (int i = 0; i < paramList.size(); i++) {
            KeyValue kv = paramList.get(i);
            final String key = kv.key;
            if (TextUtils.isEmpty(key)) continue;

            JSONArray ja = null;
            if (tempData.containsKey(key)) {
                ja = tempData.get(key);
            } else {
                ja = new JSONArray();
                tempData.put(key, ja);
            }

            ja.put(RequestParamsHelper.parseJSONObject(kv.value));

            if (kv instanceof ArrayItem) {
                arraySet.add(key);
            }
        }

        for (Map.Entry<String, JSONArray> entry : tempData.entrySet()) {
            String key = entry.getKey();
            JSONArray ja = entry.getValue();
            if (ja.length() > 1 || arraySet.contains(key)) {
                jsonObject.put(key, ja);
            } else {
                Object value = ja.get(0);
                jsonObject.put(key, value);
            }
        }
    }

    private final static class ArrayItem extends KeyValue {
        public ArrayItem(String key, Object value) {
            super(key, value);
        }
    }
    static class LogInterceptor implements Interceptor {
        private static final String TAG = "OkHttp";

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.v(TAG, "request:" + request.toString());
            long t1 = System.nanoTime();
            Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Log.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.i(TAG, "response body:" + content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }

    private static String getJson(Map<String,Object> map){
        JSONObject Jsonobject = new JSONObject();
        List<KeyValue> paramList = new ArrayList<KeyValue>();

        for (String key : map.keySet()) {
            paramList.add(new KeyValue(key, map.get(key)));
        }
        try {
            params2Json(Jsonobject, paramList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return String.valueOf(Jsonobject);
    }



}
