package com.tiens.comonlibrary.cache

import com.tiens.comonlibrary.application.BaseApplication

class ACCache : ICache {
    override fun saveCache(key: String, value: String, cacheTime: Long) {
        ACache.get(BaseApplication.getAppContext()).put(key, value, cacheTime.toInt())
    }

    override fun getCache(key: String): String? {
        return ACache.get(BaseApplication.getAppContext()).getAsString(key)
    }
}