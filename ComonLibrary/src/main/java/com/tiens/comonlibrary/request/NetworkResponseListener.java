package com.tiens.comonlibrary.request;

/**
 * @author: lhc
 * @date: 2020-03-19 21:51
 * @description
 */

public interface NetworkResponseListener {
    void onSuccess(String response);
    void onFail(ApiException e);
}
