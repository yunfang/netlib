package com.cheers.okhttplibrary.bean;

import java.io.Serializable;

/**
 * Created by zhouyunfang on 17/6/1.
 */

public class BaseResult implements Serializable {

    private int code; //

    private String msg;

    private String tokenFail;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTokenFail() {
        return tokenFail;
    }

    public void setTokenFail(String tokenFail) {
        this.tokenFail = tokenFail;
    }
}
