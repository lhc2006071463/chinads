package com.tiens.chinads

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tiens.chinads.databinding.ActivityMainBinding
import com.tiens.chinads.owner.OwnerDataProvider
import com.tiens.comonlibrary.base.ui.BaseVMActivity
import com.tiens.chinads.res.route.RouterPaths
import com.tiens.comonlibrary.annotation.HookClick
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.request.ExceptionUtil
import com.tiens.comonlibrary.util.RouteUtil
import com.tiens.comonlibrary.util.UIUtil
import com.tiens.comonlibrary.util.dp
import com.tiens.comonlibrary.util.generateStrokeDrawable
import kotlinx.android.synthetic.main.activity_main.*

@HookClick(R.id.tv_click)
@Route(path = RouterPaths.Main.MAIN_ACTIVITY)
class MainActivity : BaseVMActivity<ActivityMainBinding,MainVM>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {
        mRootBinding.titleBar.setTitle("Main Activity")
    }

    override fun initView() {
        binding.tvClick.background = generateStrokeDrawable(1.dp.toInt(), Color.parseColor("#ff0000"),5.dp.toInt())
        supportFragmentManager.beginTransaction().add(R.id.fl_container,TestFragment(),"TestFragment").commitAllowingStateLoss()
    }

    override fun initData() {
        val ownerDataProvider = ARouter.getInstance().build(RouterPaths.Owner.OWNER_DATA_PROVIDER).navigation() as OwnerDataProvider
        ownerDataProvider.getOwnerData()
        addObservers()

    }

    override fun initListeners() {
        binding.tvClick.setOnClickListener {
            Log.d("Tag","onClick")
//            mVM.getData()
            RouteUtil.navigation(RouterPaths.Owner.OWNER_ACTIVITY)
        }
    }

    private fun addObservers() {
        mVM.datas.observe(this, Observer {
            toast(it[0].sid)
            val appDataProvider = ARouter.getInstance().build(RouterPaths.Main.APP_DATA_PROVIDER).navigation() as AppDataProvider
            appDataProvider.content = it[0].toString()
//            ARouter.getInstance().build(RouterPaths.Owner.OWNER_ACTIVITY).navigation()
        })

        ExceptionUtil.getInstance().exceptionLiveData.observe(this, Observer {
            if(mResumed)
                Log.e("Tag","MainActivity 接收到错误消息")
        })
    }
}