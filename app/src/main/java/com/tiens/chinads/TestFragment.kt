package com.tiens.chinads

import android.util.Log
import androidx.lifecycle.Observer
import com.tiens.chinads.databinding.FragmentTestBinding
import com.tiens.comonlibrary.base.EmptyVM
import com.tiens.comonlibrary.base.ui.BaseVMFragment
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.request.ExceptionUtil

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
//        ExceptionUtil.getInstance().exceptionLiveData.observe(this, Observer {
//            Log.e("Tag","TestFragment 接收到错误消息")
//        })
    }
}