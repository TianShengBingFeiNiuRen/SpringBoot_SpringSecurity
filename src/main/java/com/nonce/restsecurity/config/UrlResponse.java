package com.nonce.restsecurity.config;

import java.io.Serializable;

/**
 * @author Andon
 * @date 2019/3/20
 *
 * 返回给前端的json数据格式
 */
public class UrlResponse implements Serializable {

    private boolean success;
    private String code;
    private String message;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

