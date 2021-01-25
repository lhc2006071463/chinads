package com.tiens.chinads

import android.os.Bundle
import android.view.View
import com.tiens.chinads.commonaop.AOP
import com.tiens.chinads.commonaop.annotation.FastClickTrace
import com.tiens.chinads.commonaop.annotation.LoginTrace
import com.tiens.chinads.commonaop.annotation.PermissionTrace
import com.tiens.chinads.commonaop.util.PermissionGroup
import com.tiens.chinads.databinding.ActivityRefreshBinding
import com.tiens.comonlibrary.base.ui.BaseRefreshVMActivity
import com.tiens.comonlibrary.util.ALog

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
//    @PermissionTrace(value = [PermissionGroup.CAMERA,PermissionGroup.STORAGE], pageName = "RefreshActivity")
    private fun getData(it: View) {
        mVM.getData()
    }

    override fun initRefreshLayout() {
        mRefreshLayout = binding.refreshLayout
    }

    override fun initData() {

    }

    override fun initView() {

    }
}