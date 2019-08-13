package com.cheers.okhttplibrary.net.network;


/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/8
 * 描      述 ：
 * =======================================================================
 */
public interface RequestCallback<R> {
    /**
     * 当请求成功后回调
     *
     * @param dataResult 数据结果
     */
    public void onRequestSuccess(R dataResult, String result);

    /**
     *
     * 当请求失败后回调
     *
     * @param response 数据结果
     */
    public void onRequestFailure(String response, String errorMsg);
}
