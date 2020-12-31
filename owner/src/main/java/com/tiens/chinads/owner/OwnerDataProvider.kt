package com.tiens.chinads.owner

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.tiens.comonlibrary.dataprovider.IOwnerDataProvider
import com.tiens.chinads.res.route.RouterPaths

@Route(path = RouterPaths.Owner.OWNER_DATA_PROVIDER)
class OwnerDataProvider: IOwnerDataProvider {
    override fun getOwnerData(): String {
        return "";
    }

    override fun init(context: Context?) {

    }
}