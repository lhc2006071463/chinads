package com.tiens.comonlibrary.cache

interface ICache {
    fun saveCache(key: String, value: String, cacheTime: Long)
    fun getCache(key: String): String?
}