package com.tiens.comonlibrary.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tiens.comonlibrary.util.ALog;
import org.json.JSONObject;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: lhc
 * @date: 2020-03-19 21:50
 * @description String 转成泛型具体类
 */

public abstract class IDataCallback<T> implements NetworkResponseListener {
    @Override
    public void onSuccess(String response) {
        Gson gson = new Gson();
        Type type = getClassType(this);
        try {
            JSONObject jsonObject = new JSONObject(response);
            Object data = jsonObject.opt("result"); //result
            if (data != null) {
                HttpResult entity = gson.fromJson(response, new TypeToken<HttpResult>() {}.getType());
                T t = gson.fromJson(data.toString(), type);
                if (entity != null) {
                    onSuccess(entity, t);
//                    if (entity.getCode() == 10000)
//                        onSuccess(entity, t);
//                    else
//                        onFail(new ApiException(entity.getCode(), entity.getMsg()));
                }
            }
        } catch (Exception e) {
            onFail(new ApiException(ApiException.UNKNOWN_ERROR, e.getMessage()));
            e.printStackTrace();
        }

    }

    private Type getClassType(Object obj) {
        Type genType = obj.getClass().getGenericSuperclass();
        Type[] args = ((ParameterizedType) genType).getActualTypeArguments();
        return args[0];
    }

    public abstract void onSuccess(HttpResult result, T t);
}