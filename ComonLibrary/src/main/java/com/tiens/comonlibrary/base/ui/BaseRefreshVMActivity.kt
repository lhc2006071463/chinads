package com.tiens.comonlibrary.base.ui

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.tiens.comonlibrary.base.BaseRefreshVM

abstract class BaseRefreshVMActivity<VB : ViewDataBinding, VM : BaseRefreshVM> : BaseVMActivity<VB, VM>() {
    override fun addCommonObservers() {
        super.addCommonObservers()
        mVM.mFinishRefreshData.observe(this, Observer<Boolean> {

        })
    }
}