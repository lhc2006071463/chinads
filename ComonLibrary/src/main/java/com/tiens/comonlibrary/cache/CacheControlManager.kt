package com.tiens.comonlibrary.cache

import android.text.TextUtils
import com.tiens.comonlibrary.api.ApiManager
import com.tiens.comonlibrary.application.BaseApplication
import org.json.JSONException
import org.json.JSONObject

object CacheControlManager {
    private var mCache: ICache? = null

    private fun getCacheTime(url: String): Int {
        if (TextUtils.isEmpty(url)) return 0
        return if (url.contains(ApiManager.Main.GET_DATA)) {
            7 * ACache.TIME_DAY
        } else 0
    }

    fun getCacheData(url: String, queryMap: Map<String, Any>?): String? {
        return mCache?.getCache(getCacheKey(url, queryMap))
    }

    private fun getCacheKey(url: String, queryMap: Map<String, Any>?): String {
        val sb = StringBuilder()
        sb.append(url)
        if (queryMap != null && queryMap.isNotEmpty()) {
            val keys = queryMap.keys
            sb.append("?")
            for (key in keys) {
                sb.append(key).append("=").append(queryMap[key])
            }
            //add headers
        }
        return sb.toString()
    }

    @Synchronized
    fun setCache(url: String, queryMap: Map<String, Any>?, value: String?) {
        BaseApplication.getAppContext().appExecutors.diskIO().execute { mCache!!.saveCache(getCacheKey(url, queryMap), value!!, getCacheTime(url).toLong()) }
    }

    fun cacheResponse(url: String, paramMap: Map<String, Any>?, bodyString: String?) {
        try {
            val objBody = JSONObject(bodyString?:"")
            val code = objBody.optInt("code")
            if (code == 200) {
                setCache(url, paramMap, bodyString)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    init {
        mCache = ACCache()
    }
}