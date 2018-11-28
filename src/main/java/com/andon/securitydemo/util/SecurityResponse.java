package com.andon.securitydemo.util;

import java.io.Serializable;

/**
 * 用户角色资源管理
 */
public class SecurityResponse implements Serializable {

    private String status; //状态码：-1失败 1成功
    private String message; //信息说明
    private Object result; //数据结果集

    public SecurityResponse() {
    }

    public SecurityResponse(String status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
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
        return "SecurityResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
