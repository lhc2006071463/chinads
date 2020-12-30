package com.tiens.comonlibrary.request;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ResponseTransfer {
    private NetworkResponseListener listener;
    public ResponseTransfer(NetworkResponseListener listener) {
        this.listener = listener;
    }
    public void transfer(Response<ResponseBody> response) {
        try {
            if (response.code() == 200) {
                String body = response.body().string();
                if(listener!=null&&!TextUtils.isEmpty(body)) {
                    listener.onSuccess(body);
                }
            } else {
                String errorMsg = response.errorBody().string();
                if(listener!=null)
                    listener.onFail(new ApiException(response.code(), errorMsg));
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = e.getMessage();
            if(listener!=null)
                listener.onFail(new ApiException(response.code(), errorMsg));
        }
    }

}