package com.tiens.chinads

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.gson.reflect.TypeToken
import com.tiens.chinads.databinding.ActivityRefreshBinding
import com.tiens.comonlibrary.annotation.HookClick
import com.tiens.comonlibrary.api.ApiManager
import com.tiens.comonlibrary.base.ui.BaseRefreshVMActivity
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.request.ExceptionUtil
import com.tiens.comonlibrary.request.HttpResult
import com.tiens.comonlibrary.util.ALog
import com.tiens.comonlibrary.util.GsonUtil
import com.tiens.comonlibrary.util.HookClickUtil
import com.tiens.comonlibrary.util.ToastUtils
import kotlinx.coroutines.launch
import org.json.JSONObject

@HookClick(R.id.tv_click)
class RefreshActivity : BaseRefreshVMActivity<ActivityRefreshBinding,RefreshVM>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_refresh
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {
    }

    override fun initListeners() {
//        window.decorView.post {
//            val curTime = System.currentTimeMillis()
//            for(i in 0..1000)
//                HookClickUtil.hookView(binding.tvClick)
//            Log.e("Tag",(System.currentTimeMillis() - curTime).toString())
//        }
        binding.tvClick.setOnClickListener{
            getData(it)
        }
        mRefreshLayout.setOnRefreshListener {
            mVM.getData()
        }

    }

    private fun getData(it: View) {
//        ARouter.getInstance().build(RouterPaths.Owner.OWNER_ACTIVITY).navigation()
        lifecycleScope.launch {
            ALog.d(Thread.currentThread().name)
//            val response1 = async { mVM.get("http://192.168.31.72:8005/conferenceItem/service/all")}
            val response = mVM.get(ApiManager.Main.GET_DATA)
            val jsonObject = JSONObject(response.body()?.string())
            val result = jsonObject.opt("result")
            val resultBean = GsonUtil.json2Bean<HttpResult<*>>(response.body()?.string(), object : TypeToken<HttpResult<*>?>() {}.type)
            val dataBean = GsonUtil.parseJsonToList<DataBean>(result.toString())
            Log.d("Test",dataBean.toString())
            ALog.d(dataBean.toString())
        }

        ALog.d("挂起函数外执行...")
    }

    override fun initRefreshLayout() {
        mRefreshLayout = binding.refreshLayout
    }

    override fun initData() {
        val content = "失效商品,\n失效商品1\n失效商品2"
        val result = content.replace("\n", System.getProperty("line.separator"))
        ToastUtils.showToastCenter(result,5000)

        ExceptionUtil.getInstance().exceptionLiveData.postValue(ApiException(1000,"msg"))
    }

    override fun initView() {

    }
}