package com.tiens.chinads.owner.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tiens.apt_annotation.Login
import com.tiens.chinads.owner.OwnerVM
import com.tiens.chinads.owner.R
import com.tiens.chinads.owner.databinding.OwnerActivityOwnerBinding
import com.tiens.chinads.owner.room.DbManager
import com.tiens.chinads.owner.room.User
import com.tiens.comonlibrary.base.EmptyVM
import com.tiens.comonlibrary.base.ui.BaseVMActivity
import com.tiens.chinads.res.route.RouterPaths
import com.tiens.comonlibrary.dataprovider.IAppDataProvider
import com.tiens.comonlibrary.util.ALog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Route(path = RouterPaths.Owner.OWNER_ACTIVITY)
class OwnerActivity : BaseVMActivity<OwnerActivityOwnerBinding, OwnerVM>() {
    override fun getLayoutId(): Int {
        return R.layout.owner_activity_owner
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {
    }

    override fun initView() {
    }

    override fun initData() {
//        val appDataProvider = ARouter.getInstance().build(RouterPaths.Main.APP_DATA_PROVIDER).navigation() as IAppDataProvider
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                val response = appDataProvider.getAppData()
//                val content = response.body()?.string()?:""
//                Log.d("Tag", "======>$content")
//            }
//        }
//        mVM.getData()
    }

    override fun initListeners() {
        binding.tvClick.setOnClickListener {
            getData()
        }
    }

    private fun getData() {
        val user = User(0,"test","test123")
        val user1 = User(0,"test","test234")
        val user2 = User(0,"test","test456")
        lifecycleScope.launch(Dispatchers.IO) {
            DbManager.getDb().userDao().insert(user)
            DbManager.getDb().userDao().insert(user1)
            DbManager.getDb().userDao().insert(user2)
            val all = DbManager.getDb().userDao().getAll()
            for(item in all) {
                ALog.d(item.lastName+"==="+item.uid)
            }
        }

    }
}