package com.nonce.restsecurity.config;

import java.io.Serializable;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 返回给前端的json数据格式
 */
public class UrlResponse implements Serializable {

    private boolean success;
    private String code;
    private String message;
    private Object data;
    private int total;

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
        return "UrlResponse{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}

