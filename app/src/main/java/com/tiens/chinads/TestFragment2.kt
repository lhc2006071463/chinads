package com.tiens.chinads

import com.tiens.chinads.databinding.FragmentTest2Binding
import com.tiens.comonlibrary.base.EmptyVM
import com.tiens.comonlibrary.base.ui.BaseVMFragment

class TestFragment2 : BaseVMFragment<FragmentTest2Binding,EmptyVM>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_test2
    }

    override fun initListener() {
        mBinding.tvTest.setOnClickListener {
            mContext?.showFragment(R.id.fl_container,TestFragment())
        }
    }

    override fun initTitleBar() {
    }

    override fun initData() {
    }
}