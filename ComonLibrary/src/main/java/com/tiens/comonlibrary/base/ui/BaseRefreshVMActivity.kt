package com.tiens.comonlibrary.base.ui

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.tiens.comonlibrary.annotation.HookClick
import com.tiens.comonlibrary.base.BaseRefreshVM
import com.tiens.comonlibrary.util.HookClickUtil
import com.tiens.comonlibrary.widget.CommonRefreshLayout

abstract class BaseRefreshVMActivity<VB : ViewDataBinding, VM : BaseRefreshVM> : BaseVMActivity<VB, VM>() {
    lateinit var mRefreshLayout: CommonRefreshLayout
    override fun setData() {
        initRefreshLayout()
        super.setData()
    }
    abstract fun initRefreshLayout()

    override fun addCommonObservers() {
        super.addCommonObservers()
        mVM.mFinishRefreshData.observe(this, Observer<Boolean> {
            if(mRefreshLayout.state==RefreshState.Refreshing) mRefreshLayout.finishRefresh() else mRefreshLayout.finishLoadMore()
        })
    }
}