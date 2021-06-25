package com.tiens.comonlibrary.request;

/**
 * @author: lhc
 * @date: 2020-03-19 21:51
 * @description
 */

public interface PageResponseListener<T> {
    void onSuccess(int page, HttpResult result, T t);
    void onFail(ApiException e);
}
