package com.tiens.comonlibrary.request;

/**
 * @author: lhc
 * @date: 2020-03-19 21:49
 * @description 网络请求返回基类
 */

public class HttpResult<T> {
    private int code;
    private String msg;
    private T data;
    private ApiException apiException;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiException getApiException() {
        return apiException;
    }

    public void setApiException(ApiException apiException) {
        this.apiException = apiException;
    }
}
