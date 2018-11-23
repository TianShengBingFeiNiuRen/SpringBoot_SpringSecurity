package com.andon.securitydemo.security.bean;

import java.io.Serializable;

/**
 * 返回给前端JSON的数据格式
 */
public class UrlResponse implements Serializable {

    private String status;
    private String message;
    private Object result;

    public UrlResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UrlResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
