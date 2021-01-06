package com.tiens.chinads

import com.tiens.chinads.databinding.FragmentTestBinding
import com.tiens.comonlibrary.base.EmptyVM
import com.tiens.comonlibrary.base.ui.BaseVMFragment

class TestFragment : BaseVMFragment<FragmentTestBinding,EmptyVM>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_test
    }

    override fun initListener() {
        mBinding.tvTest.setOnClickListener {
            mContext?.showFragment(R.id.fl_container,TestFragment2())
        }
    }

    override fun initTitleBar() {
    }

    override fun initData() {
    }
}