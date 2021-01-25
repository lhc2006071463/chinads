package com.tiens.chinads.owner

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.tiens.chinads.owner.bean.UserInfo
import com.tiens.comonlibrary.dataprovider.IOwnerDataProvider
import com.tiens.chinads.res.route.RouterPaths

@Route(path = RouterPaths.Owner.OWNER_DATA_PROVIDER)
class OwnerDataProvider: IOwnerDataProvider {
    override fun getOwnerData(): String {
        val info = UserInfo()
        info.name = "zhang"
        info.age = 20
        return "123";
    }

    override fun init(context: Context?) {

    }
}