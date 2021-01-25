package com.tiens.chinads

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.tiens.chinads.res.route.RouterPaths.Main.Companion.APP_DATA_PROVIDER
import com.tiens.comonlibrary.dataprovider.IAppDataProvider

@Route(path = APP_DATA_PROVIDER)
class AppDataProvider : IAppDataProvider {
    var content: String = ""
    override fun getAppData(): String {
        return content
    }

    override fun init(context: Context?) {

    }
}