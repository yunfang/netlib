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
package com.cheers.okhttplibrary.utils.okhttp.download.okgo.exception;

/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/5
 * 描      述 ：
 * =======================================================================
 */
public class OkGoException extends Exception {
    private static final long serialVersionUID = -8641198158155821498L;

    public OkGoException(String detailMessage) {
        super(detailMessage);
    }

    public static OkGoException UNKNOWN() {
        return new OkGoException("unknown exception!");
    }

    public static OkGoException BREAKPOINT_NOT_EXIST() {
        return new OkGoException("breakpoint file does not exist!");
    }

    public static OkGoException BREAKPOINT_EXPIRED() {
        return new OkGoException("breakpoint file has expired!");
    }
}
