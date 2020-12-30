package com.tiens.chinads

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.tiens.chinads.databinding.ActivityMainBinding
import com.tiens.chinads.owner.OwnerDataProvider
import com.tiens.comonlibrary.base.ui.BaseVMActivity
import com.tiens.comonlibrary.dataprovider.IOwnerDataProvider
import com.tiens.comonlibrary.route.RouterPaths
import com.tiens.comonlibrary.util.ALog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseVMActivity<ActivityMainBinding,MainVM>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {

    }

    override fun initView() {

    }

    override fun initData() {
        val ownerDataProvider = ARouter.getInstance().build(RouterPaths.OWNER_DATA_PROVIDER).navigation() as OwnerDataProvider
        ownerDataProvider.getOwnerData()
        addObservers()
    }

    override fun initListeners() {
        tv_click.setOnClickListener {
            Log.d("Tag","onClick")
            mVM.getData()
        }
    }

    private fun addObservers() {
        mVM.datas.observe(this, Observer {
            toast(it[0].sid)
        })
    }
}