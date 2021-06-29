package com.tiens.comonlibrary.base.ui

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.tiens.comonlibrary.base.BaseRefreshVM
import com.tiens.comonlibrary.widget.CommonRefreshLayout

abstract class BaseRefreshVMActivity<VB : ViewDataBinding, VM : BaseRefreshVM> : BaseVMActivity<VB, VM>() {
    lateinit var mRefreshLayout: CommonRefreshLayout
    override fun setData() {
        findRefreshLayout(binding.root)
        super.setData()
    }

    private fun findRefreshLayout(rootView: View) {
        if(rootView is ViewGroup) {
            if(rootView is CommonRefreshLayout) {
                mRefreshLayout = rootView
                return
            }
            for(view in rootView.children) {
                if(view is CommonRefreshLayout) {
                    mRefreshLayout = view
                    return
                }else {
                    findRefreshLayout(view)
                }
            }
        }
    }

    override fun addCommonObservers() {
        super.addCommonObservers()
        mVM.mFinishRefreshData.observe(this, Observer<Boolean> {
            if(mRefreshLayout.state==RefreshState.Refreshing) mRefreshLayout.finishRefresh() else mRefreshLayout.finishLoadMore()
        })
    }
}