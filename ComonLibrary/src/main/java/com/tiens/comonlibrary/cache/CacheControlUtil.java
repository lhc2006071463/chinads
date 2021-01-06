package com.tiens.comonlibrary.cache;

import android.text.TextUtils;

import com.tiens.comonlibrary.api.ApiManager;
import com.tiens.comonlibrary.application.BaseApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

public class CacheControlUtil {
    public static final String[] APIS = {ApiManager.Main.GET_DATA};

    public static boolean shouldCacheData(String url) {
        for(String item: APIS) {
            if(url.contains(item))
                return true;
        }
        return false;
    }

    public static int getCacheTime(String url) {
        if(TextUtils.isEmpty(url)) return 0;
        if(url.contains(ApiManager.Main.GET_DATA)) {
            return 7*ACache.TIME_DAY;
        }
        return 0;
    }

    public static String getCacheData(String url, Map<String, Object> queryMap) {
        return ACache.get(BaseApplication.getAppContext()).getAsString(getCacheKey(url,queryMap));
    }

    private static String getCacheKey(String url, Map<String, Object> queryMap) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (queryMap != null && !queryMap.isEmpty()) {
            Set<String> keys = queryMap.keySet();
            sb.append("?");
            for (String key : keys) {
                sb.append(key).append("=").append(queryMap.get(key));
            }
        }
        return sb.toString();
    }


    public static synchronized void setCache(String url, Map<String, Object> queryMap, String value) {
        BaseApplication.getAppContext().getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ACache.get(BaseApplication.getAppContext()).put(getCacheKey(url,queryMap),value,getCacheTime(url));
            }
        });
    }

    public static void cacheResponse(String url, Map<String,Object> paramMap, String bodyString) {
        try {
            JSONObject object = new JSONObject(bodyString);
            int code = object.optInt("code");
            if (code == 200) {
                setCache(url, paramMap, bodyString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
