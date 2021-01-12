package com.tiens.comonlibrary.base.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.tiens.comonlibrary.R
import com.tiens.comonlibrary.base.BaseViewModel
import com.tiens.comonlibrary.databinding.LayoutBaseContentBinding
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.util.*
import com.tiens.comonlibrary.util.glide.ImageUtils
import com.tiens.comonlibrary.util.vm.getVmClazz

/**
 * @author: lhc
 * @date: 2020-03-20 23:28
 * @description Activity 基类
 */
abstract class BaseVMActivity<VB : ViewDataBinding, VM : BaseViewModel> : FragmentActivity() {
    lateinit var mRootBinding: LayoutBaseContentBinding
    var mContext: Activity? = null
    lateinit var binding: VB
    lateinit var mVM: VM
    var mResumed: Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initBeforeSetContent()
        mRootBinding = DataBindingUtil.setContentView(this, R.layout.layout_base_content)
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutId(), null, false)
        addContentView()
        ARouter.getInstance().inject(this)
        initVM()
        setData()
        initTitleBar(savedInstanceState)
        ALog.d(this.javaClass.simpleName)
    }

    private fun initVM() {
        mVM = ViewModelProvider(this).get(getVmClazz(this))
        registerCommonObservers()
    }

    private fun addContentView() {
        if (needSetPaddingTop()) {
            setPaddingTop()
        }
        mRootBinding.rlRoot.addView(binding.root, 1)
        (binding.root.layoutParams as RelativeLayout.LayoutParams).apply {
            width = RelativeLayout.LayoutParams.MATCH_PARENT
            height = RelativeLayout.LayoutParams.MATCH_PARENT
            addRule(RelativeLayout.BELOW, R.id.titleBar)
        }

    }

    private fun setData() {
        initView()
        initListeners()
        initData()
    }

    fun setTitle(title: String?) {
        mRootBinding.titleBar.setTitle(title)
    }

    private fun initBeforeSetContent() {
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        if (transparencyBar()) {
            StatusBarUtil.transparencyBar(mContext)
            StatusBarUtil.setLightStatusBar(mContext, true)
        }
    }

    /**
     * 方法功能描述:初始化布局文件
     */
    abstract fun getLayoutId(): Int

    /**
     * 方法功能描述:初始化titleBar信息
     */
    abstract fun initTitleBar(savedInstanceState: Bundle?)

    /**
     * 初始化布局
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化监听事件
     */
    abstract fun initListeners()

    private fun registerCommonObservers() {
        mVM.errorData.observe(this, Observer<ApiException> {
                onRequestFail(it)
        })
        mVM.isShowLoading.observe(this, Observer<Boolean> {
            showLoading(it)
        })

        addCommonObservers()
    }

    open fun addCommonObservers() {}

    open fun onRequestFail(e: ApiException) {
        if(!NetworkUtil.isNetAvailable(mContext)) {
            toastCenter("网络不可用")
        }else {
            toastCenter(e.msg)
        }
    }

    override fun onResume() {
        super.onResume()
        mResumed = true
    }

    override fun onPause() {
        super.onPause()
        mResumed = false
    }

    override fun onStop() {
        super.onStop()
        mResumed = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mRootBinding.ivProgress.visibility = View.GONE
    }

    open fun needSetPaddingTop(): Boolean {
        return true
    }

    open fun transparencyBar(): Boolean {
        return true
    }

    open fun isFullScreen(): Boolean {
        return false
    }

    fun toast(message: String?) {
        ToastUtils.showToast(message, Toast.LENGTH_SHORT)
    }

    fun toastLong(message: String?) {
        ToastUtils.showToast(message, Toast.LENGTH_LONG)
    }

    open fun toastCenter(message: String?) {
        ToastUtils.showToastCenter(message, Toast.LENGTH_SHORT)
    }

    fun showLoadingView(showLoading: Boolean) {
        mRootBinding.rlLoading.visibility = if (showLoading) View.VISIBLE else View.GONE
        if (showLoading) ImageUtils.loadResource(mRootBinding.ivProgress, R.drawable.common_loading)
    }

    fun showFragment(
        containerId: Int,
        fragment: Fragment
    ) {
        val fragments =
            supportFragmentManager.fragments
        for (item in fragments) {
            supportFragmentManager.beginTransaction().hide(item!!).commit()
        }
        if (fragment.isAdded) {
            supportFragmentManager.beginTransaction().show(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().add(containerId, fragment).commit()
        }
    }

    fun hideFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction().hide(fragment!!).commit()
    }

    open fun setPaddingTop() {
        val root = mRootBinding.root
        root.setPadding(0, UIUtil.getStatusBarHeight(mContext), 0, 0)
    }

    open fun showLoading(show: Boolean) {
        showLoadingView(show)
    }
}