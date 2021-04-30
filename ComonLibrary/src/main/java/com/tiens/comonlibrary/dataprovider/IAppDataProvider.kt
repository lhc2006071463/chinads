package com.tiens.comonlibrary.dataprovider

import com.alibaba.android.arouter.facade.template.IProvider
import okhttp3.ResponseBody
import retrofit2.Response

interface IAppDataProvider : IProvider {
    suspend fun getAppData(): Response<ResponseBody>
}