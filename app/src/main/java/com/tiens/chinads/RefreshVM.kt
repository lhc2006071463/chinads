package com.tiens.chinads

import androidx.lifecycle.MutableLiveData
import com.tiens.comonlibrary.api.ApiManager
import com.tiens.comonlibrary.base.BaseRefreshVM
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.request.HttpResult
import com.tiens.comonlibrary.request.IDataCallback

class RefreshVM : BaseRefreshVM() {
    var datas = MutableLiveData<List<DataBean>>()
    fun getData() {
//        get(ApiManager.Main.GET_DATA, mapOf<String,Any>(), DataEnum.CACHE_ONLY, true, object : IDataCallback<List<DataBean>>() {
//            override fun onSuccess(result: HttpResult<*>, t: List<DataBean>) {
//                datas.value = t
//            }
//
//            override fun onFail(e: ApiException) {
//                errorData.value = e
//            }
//
//        })
        get(ApiManager.Main.GET_DATA, true, object : IDataCallback<List<DataBean>>() {
            override fun onSuccess(result: HttpResult<*>, t: List<DataBean>) {
                datas.value = t
            }

            override fun onFail(e: ApiException) {
                errorData.value = e
            }

        })
    }
}