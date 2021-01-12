package com.tiens.comonlibrary.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tiens.comonlibrary.base.BaseViewModel
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.util.ALog
import com.tiens.comonlibrary.util.NetworkUtil
import com.tiens.comonlibrary.util.ToastUtils
import com.tiens.comonlibrary.util.vm.getVmClazz

abstract class BaseVMFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    var mRootView: ViewGroup? = null
    var mContext: BaseVMActivity<*,*>? = null
    lateinit var mBinding: VB
    lateinit var mVM: VM
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as BaseVMActivity<*,*>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ALog.d(javaClass.simpleName)
        if (mRootView != null) {
            val parent = mRootView!!.parent as ViewGroup
            parent.removeView(mRootView)
        } else {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            mRootView = mBinding.root as ViewGroup
            setData()
        }
        return mRootView
    }

    private fun setData() {
        initVM()
        initData()
        initTitleBar()
        initListener()
    }

    private fun initVM() {
        mVM = ViewModelProvider(this).get(getVmClazz(this))
        registerCommonObservers()
    }

    abstract fun getLayoutId(): Int

    private fun registerCommonObservers() {
        mVM.errorData.observe(this,
            Observer<ApiException> {
                onRequestFail(it)
            })
        mVM.isShowLoading.observe(this, Observer<Boolean> {
            showLoading(it)
        })
    }

    /**
     * 方法功能描述:初始化监听事件
     */
    abstract fun initListener()

    /**
     * 方法功能描述:初始化title信息
     */
    abstract fun initTitleBar()

    /**
     * 方法功能描述:初始化数据
     */
    abstract fun initData()

    open fun onRequestFail(e: ApiException) {
        if(!NetworkUtil.isNetAvailable(mContext)) {
            toastCenter("网络不可用")
        }else {
            toastCenter(e.msg)
        }
    }

    open fun showLoading(show: Boolean) {
        if (mContext is BaseVMActivity<*,*>) (mContext as BaseVMActivity<*,*>).showLoadingView(
            show
        )
    }

    fun toast(message: String?) {
        ToastUtils.showToast(message, Toast.LENGTH_SHORT)
    }

    fun toastCenter(message: String?) {
        ToastUtils.showToastCenter(message, Toast.LENGTH_SHORT)
    }

    fun needRegisterEventBus(): Boolean {
        return false
    }
}