package com.tiens.comonlibrary.dataprovider

import com.alibaba.android.arouter.facade.template.IProvider

interface IAppDataProvider : IProvider {
    fun getAppData(): String
}