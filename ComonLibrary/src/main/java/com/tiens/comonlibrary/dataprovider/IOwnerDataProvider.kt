package com.tiens.comonlibrary.dataprovider

import com.alibaba.android.arouter.facade.template.IProvider

interface IOwnerDataProvider : IProvider {
    fun getOwnerData(): String
}