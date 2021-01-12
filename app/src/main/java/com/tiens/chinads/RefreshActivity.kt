package com.tiens.chinads

import android.os.Bundle
import com.tiens.chinads.databinding.ActivityRefreshBinding
import com.tiens.comonlibrary.base.ui.BaseRefreshVMActivity

class RefreshActivity : BaseRefreshVMActivity<ActivityRefreshBinding,RefreshVM>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_refresh
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {
    }

    override fun initListeners() {
        binding.tvClick.setOnClickListener {
            mVM.getData()
        }
        mRefreshLayout.setOnRefreshListener {
            mVM.getData()
        }
    }

    override fun initRefreshLayout() {
        mRefreshLayout = binding.refreshLayout
    }

    override fun initData() {

    }

    override fun initView() {

    }
}