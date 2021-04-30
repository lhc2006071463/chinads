package com.tiens.chinads.owner

import com.alibaba.android.arouter.launcher.ARouter
import com.tiens.chinads.owner.bean.DataBean
import com.tiens.chinads.res.route.RouterPaths
import com.tiens.comonlibrary.base.BaseViewModel
import com.tiens.comonlibrary.dataprovider.IAppDataProvider
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.request.HttpResult
import com.tiens.comonlibrary.request.IDataCallback

class OwnerVM : BaseViewModel() {
    fun getData() {
        val appDataProvider = ARouter.getInstance().build(RouterPaths.Main.APP_DATA_PROVIDER).navigation() as IAppDataProvider
        request({appDataProvider.getAppData()},true,object : IDataCallback<List<DataBean>>() {
            override fun onSuccess(result: HttpResult<*>?, t: List<DataBean>) {

            }

            override fun onFail(e: ApiException?) {

            }

        })
    }
}