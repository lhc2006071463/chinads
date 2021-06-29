package com.tiens.chinads

import androidx.lifecycle.MutableLiveData
import com.tiens.comonlibrary.base.BaseRefreshVM
import com.tiens.comonlibrary.base.BaseViewModel
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.request.HttpResult
import com.tiens.comonlibrary.request.IDataCallback

class TestRefresh2VM : BaseRefreshVM() {
    val successData = MutableLiveData<RefreshDataResponse>()
    val pageErrorData = MutableLiveData<ApiException>()
    fun getData(page: Int) {

        post("http://8.131.233.244:8005/conference/service/all/$page/10", mutableMapOf(),false,
            object : IDataCallback<RefreshDataResponse>(){
            override fun onSuccess(result: HttpResult<*>?, t: RefreshDataResponse) {
                successData.value = t
            }

            override fun onFail(e: ApiException) {
                pageErrorData.value = e
            }

        })
    }
}