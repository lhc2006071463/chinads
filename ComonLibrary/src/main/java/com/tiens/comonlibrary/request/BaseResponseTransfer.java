package com.tiens.comonlibrary.request;

import okhttp3.ResponseBody;
import retrofit2.Response;

public interface BaseResponseTransfer<T> {
    void transfer(Response<ResponseBody> response);
}
