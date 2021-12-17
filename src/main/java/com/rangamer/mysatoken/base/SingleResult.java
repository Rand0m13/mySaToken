package com.rangamer.mysatoken.base;


import java.io.Serializable;


public class SingleResult<T> implements Serializable {
    private static final long serialVersionUID = 8172315972492346878L;
    private boolean success;
    private String code;
    private String message;
    private T data;

    public SingleResult() {
        this(true, "", (T) null);
    }

    public SingleResult(boolean success, String message) {
        this(success, message, (T) null);
    }

    public SingleResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
