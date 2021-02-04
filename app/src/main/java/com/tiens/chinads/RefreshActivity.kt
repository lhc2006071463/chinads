package com.tiens.chinads

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.gson.reflect.TypeToken
import com.tiens.chinads.commonaop.annotation.FastClickTrace
import com.tiens.chinads.commonaop.annotation.PermissionTrace
import com.tiens.chinads.commonaop.util.PermissionGroup
import com.tiens.chinads.databinding.ActivityRefreshBinding
import com.tiens.comonlibrary.api.ApiManager
import com.tiens.comonlibrary.base.ui.BaseRefreshVMActivity
import com.tiens.comonlibrary.request.HttpResult
import com.tiens.comonlibrary.util.ALog
import com.tiens.comonlibrary.util.GsonUtil
import kotlinx.coroutines.launch
import org.json.JSONObject

class RefreshActivity : BaseRefreshVMActivity<ActivityRefreshBinding,RefreshVM>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_refresh
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {
    }

    override fun initListeners() {
        binding.tvClick.setOnClickListener{
            getData(it)
        }
        mRefreshLayout.setOnRefreshListener {
            mVM.getData()
        }
    }

    @FastClickTrace
    @PermissionTrace(value = [PermissionGroup.CAMERA,PermissionGroup.STORAGE], pageName = "RefreshActivity")
    private fun getData(it: View) {
//        ARouter.getInstance().build(RouterPaths.Owner.OWNER_ACTIVITY).navigation()
        lifecycleScope.launch {
            val response = mVM.get(ApiManager.Main.GET_DATA)
            val jsonObject = JSONObject(response.body()?.string())
            val result = jsonObject.opt("result")
            val resultBean = GsonUtil.json2Bean<HttpResult<*>>(response.body()?.string(), object : TypeToken<HttpResult<*>?>() {}.type)
            val dataBean = GsonUtil.parseJsonToList<DataBean>(result.toString())
            ALog.d(dataBean.toString())
        }
//        mVM.getData()
    }

    override fun initRefreshLayout() {
        mRefreshLayout = binding.refreshLayout
    }

    override fun initData() {

    }

    override fun initView() {

    }
}