ppackage com.tiens.chinads

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.tiens.chinads.res.route.RouterPaths.Main.Companion.APP_DATA_PROVIDER
import com.tiens.comonlibrary.api.ApiManager
import com.tiens.comonlibrary.dataprovider.IAppDataProvider
import com.tiens.comonlibrary.request.RetrofitClient
import com.tiens.comonlibrary.util.ALog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import okhttp3.ResponseBody
import retrofit2.Response

@Route(path = APP_DATA_PROVIDER)
class AppDataProvider : IAppDataProvider {
    private var context: Context? = null
    var content: String = ""
    override suspend fun getAppData(): Response<ResponseBody> {
        return RetrofitClient.getInstance().create().get("http://192.168.31.250:8001/eduservice/teacher/pageTeacher", mutableMapOf<String,Any>().apply {
            put("current",1)
            put("pageSize",10)
        })
    }

    override fun init(context: Context?) {
        this.context = context
    }
}