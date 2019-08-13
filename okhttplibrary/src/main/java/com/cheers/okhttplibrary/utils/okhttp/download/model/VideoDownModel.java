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
package com.cheers.okhttplibrary.utils.okhttp.download.model;

import java.io.Serializable;
import java.util.Random;

/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/5
 * 描      述 ：
 * =======================================================================
 */
public class VideoDownModel implements Serializable {
    private static final long serialVersionUID = 2072893447591548402L;

    public String name;//视频名称
    public String url;//下载url
    public String iconUrl;//封面图
    public String videoid;//视频id
    public String description;//描述
    public String videolength;//视频长度
    public String category;//类别
    public int priority;
    public Object videoDetailResult;

    public VideoDownModel() {
        Random random = new Random();
        priority = random.nextInt(100);
    }
}
