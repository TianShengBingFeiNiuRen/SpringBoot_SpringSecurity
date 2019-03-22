package com.nonce.restsecurity.util;

import java.io.Serializable;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 用户角色资源管理
 */
public class SecurityResponse implements Serializable {

    private boolean success;
    private String code; //状态码：-1失败 1成功
    private String message; //信息说明
    private Object data; //数据结果集
    private int total; //总条数

    public SecurityResponse() {
    }

    public SecurityResponse(boolean success, String code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public SecurityResponse(boolean success, String code, String message, Object data, int total) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SecurityResponse{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}
