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
package com.cheers.okhttplibrary.utils.okhttp.download.server;


import com.cheers.okhttplibrary.utils.okhttp.download.okgo.model.Progress;

/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/5
 * 描      述 ：
 * =======================================================================
 */
public interface ProgressListener<T> {
    /** 成功添加任务的回调 */
    void onStart(Progress progress);

    /** 下载进行时回调 */
    void onProgress(Progress progress);

    /** 下载出错时回调 */
    void onError(Progress progress);

    /** 下载完成时回调 */
    void onFinish(T t, Progress progress);

    /** 被移除时回调 */
    void onRemove(Progress progress);
}
