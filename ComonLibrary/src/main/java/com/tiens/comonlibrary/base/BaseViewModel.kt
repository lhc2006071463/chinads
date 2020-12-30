package com.tiens.comonlibrary.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.request.NetworkResponseListener
import com.tiens.comonlibrary.request.ResponseTransfer
import com.tiens.comonlibrary.request.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

open class BaseViewModel : ViewModel() {

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
    fun <T> request(
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
                dismissLoading()
            }
        }
    }

    suspend fun get(url: String): Response<ResponseBody> {
        return httpUtil.create().get(url)
    }

    suspend fun get(url: String, params: Map<String,Any>): Response<ResponseBody> {
        return httpUtil.create().get(url,params)
    }

}
