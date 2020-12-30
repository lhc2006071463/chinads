package com.tiens.comonlibrary.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: lhc
 * @date: 2020-12-30 11:32
 * @description
 */
public class GsonUtil {
    /**
     * @param json  json字符串
     * @param clazz
     * @param <T>
     * @return
     * @brief 将JSON转为实体
     */
    public static <T> T json2Bean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T json2Bean(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * @param obj
     * @return
     * @brief 将一个对象装为Json格式的字符串
     */
    public static String bean2json(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * 把json字符串解析成集合
     * params: new TypeToken<List<AppInfo>>(){}.getType(),
     *
     * @param json type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> parseJsonToList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }
}
