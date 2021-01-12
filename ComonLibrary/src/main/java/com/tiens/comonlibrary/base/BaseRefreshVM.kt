package com.tiens.comonlibrary.base

import androidx.lifecycle.MutableLiveData

open class BaseRefreshVM : BaseViewModel() {
    val mFinishRefreshData = MutableLiveData<Boolean>()

    override fun requestFinish() {
        super.requestFinish()
        mFinishRefreshData.value = true
    }
}