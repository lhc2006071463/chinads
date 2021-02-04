package com.tiens.comonlibrary.base

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiens.comonlibrary.cache.CacheControlManager
import com.tiens.comonlibrary.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
abstract class BaseViewModel : ViewModel() {

    private val httpUtil by lazy { RetrofitClient.getInstance() }

    var isShowLoading = MutableLiveData<Boolean>()//是否显示loading
    var errorData = MutableLiveData<ApiException>()//错误信息

    private fun showLoading() {
        isShowLoading.value = true
    }

    private fun dismissLoading() {
        isShowLoading.value = false
    }

    private fun showError(error: ApiException) {
        errorData.value = error
    }

    /**
     * api：即接口调用方法
     * liveData：接口请求回调数据
     * ->数据类型，表示方法返回该数据类型
     */
    fun request(
            api: suspend CoroutineScope.() -> Response<ResponseBody>,
            isShowLoading: Boolean = false,
            responseListener: NetworkResponseListener
    ) {
        if (isShowLoading) showLoading()
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {//异步请求接口
                    val response = api()
                    withContext(Dispatchers.Main) {
                        ResponseTransfer(responseListener).transfer(response)
                    }
                }
            } catch (e: Throwable) {//接口请求失败
                showError(ApiException(ApiException.API_ERROR,e.message))
            } finally {//请求结束
                if(isShowLoading)
                    dismissLoading()
            }
        }
    }

    /**
     * api：即接口调用方法
     * liveData：接口请求回调数据
     * ->数据类型，表示方法返回该数据类型
     */
    private fun requestAndCache(
            url: String,
            params: Map<String, Any>,
            api: suspend CoroutineScope.() -> Response<ResponseBody>,
            isShowLoading: Boolean = false,
            responseListener: NetworkResponseListener
    ) {
        if (isShowLoading) showLoading()
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {//异步请求接口
                    val response = api()
                    withContext(Dispatchers.Main) {
                        val responseContent = response.body()?.string()
                        if (!TextUtils.isEmpty(responseContent) && CacheControlManager.shouldCacheData(url)) {
                            CacheControlManager.cacheResponse(url, params, responseContent)
                        }
                        ResponseTransfer(responseListener).transfer(Response.success(ResponseBody.create(response.body()?.contentType(), responseContent)))
                    }
                }
            } catch (e: Throwable) {//接口请求失败
                showError(ApiException(ApiException.API_ERROR,e.message))
            } finally {//请求结束
                dismissLoading()
                requestFinish()
            }
        }
    }

    open fun requestFinish() {}


    fun get(url: String, loading: Boolean, listener: NetworkResponseListener) {
        request({httpUtil.create().get(url)},loading, listener)
    }

    suspend fun get(url: String) : Response<ResponseBody>{
        return httpUtil.create().get(url)
    }

    fun get(url: String, params: Map<String,Any>, loading: Boolean, listener: NetworkResponseListener) {
        request({httpUtil.create().get(url,params)}, loading, listener)
    }

    private fun getCacheData(url: String, params: Map<String,Any>): Response<ResponseBody> {
        val cacheResponse = CacheControlManager.getCacheData(url,params)
        if(!cacheResponse.isNullOrEmpty()) {
            return Response.success(ResponseBody.create(null, cacheResponse))
        }
        return Response.error(8888, ResponseBody.create(null,""))
    }

    fun get(url: String, params: Map<String, Any>, dataEnum: DataEnum, loading: Boolean, listener: NetworkResponseListener) {
        val cacheResponse = CacheControlManager.getCacheData(url,params)
        if(dataEnum==DataEnum.NET_ONLY) {
            get(url,params,loading,listener)
        } else if(dataEnum==DataEnum.CACHE_ONLY) {
            if(!cacheResponse.isNullOrEmpty())
                request({getCacheData(url,params)},false, listener)
            else {
                getAndCacheResponse(url, params, loading, listener)
            }
        } else if(dataEnum==DataEnum.CACHE_NET) {
            if(!cacheResponse.isNullOrEmpty())
                request({getCacheData(url,params)},false, listener)
            getAndCacheResponse(url, params, loading, listener)
        }
    }

    private fun getAndCacheResponse(url: String, params: Map<String, Any>, loading: Boolean, listener: NetworkResponseListener) {
        requestAndCache(url, params, {if(params.isEmpty()) httpUtil.create().get(url) else httpUtil.create().get(url,params)}, loading, listener)
    }

}
