package com.tiens.comonlibrary.base.ui

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import com.tiens.comonlibrary.R
import com.tiens.comonlibrary.base.BaseViewModel
import rx.subjects.Subject

abstract class BaseDialogFragment<VB : ViewDataBinding, VM : BaseViewModel> : DialogFragment() {
    var mRootView: ViewGroup? = null
    private lateinit var mContext: BaseVMActivity<*,*>
    lateinit var binding: VB
    private lateinit var mVM: VM
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as BaseVMActivity<*,*>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(getDialogStyle(),dialogTheme())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBeforeCreateView()
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = binding.root as ViewGroup
        setWindowParams()
        setDialog()
        setData()
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(0x00000000))
            setLayout(getWidth(), getHeight())
        }
    }

    open fun setDialog() {
        val dialog = dialog
        if (dialog != null) {
            dialog.setCancelable(cancelable())
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside())
            dialog.setOnDismissListener(DialogInterface.OnDismissListener { if (dismissListener != null) dismissListener!!.onDismiss() })
        }
    }

    open fun cancelable(): Boolean {
        return true
    }

    open fun canceledOnTouchOutside(): Boolean {
        return true
    }

    open fun getDialogStyle(): Int {
        return STYLE_NO_TITLE
    }

    open fun dialogTheme(): Int {
        return R.style.DialogFragmentStyle
    }

    open fun setWindowParams() {
        val window = dialog!!.window
        // 一定要设置Background，如果不设置，window属性设置无效
        if (window != null) {
            window.setBackgroundDrawable(backGroundDrawable())
            val dm = DisplayMetrics()
            mContext.windowManager.defaultDisplay.getMetrics(dm)
            window.attributes = getLayoutParams(window)
        }
    }

    open fun getLayoutParams(window: Window): WindowManager.LayoutParams {
        val params = window.attributes
        params.gravity = gravity()
        params.width = getWidth()
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.dimAmount = dimAmount()
        if (animations() != -1) params.windowAnimations = animations()
        return params
    }

    open fun getWidth(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

    open fun getHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    open fun backGroundDrawable(): Drawable {
        return ColorDrawable(resources.getColor(android.R.color.transparent))
    }

    open fun dimAmount(): Float {
        return 0.5f
    }

    open fun gravity(): Int {
        return Gravity.BOTTOM
    }

    open fun animations(): Int {
        return -1
    }

    private fun setData() {
        intPresenter()
        initData()
        initTitleBar()
        initListener()
    }

    private fun intPresenter() {}

    abstract fun getLayoutId(): Int

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
    open fun initBeforeCreateView() {}

    fun showLoading(show: Boolean) {
        if (mContext is BaseVMActivity<*,*>) (mContext as BaseVMActivity<*,*>).showLoadingView(show)
    }

    @JvmField
    protected var listener: OnViewClickListener? = null

    interface OnViewClickListener {
        fun onViewClick(id: Int, vararg params: Any?)
    }

    open fun setOnViewClickListener(listener: OnViewClickListener?): BaseDialogFragment<*,*> {
        this.listener = listener
        return this
    }

    @JvmField
    protected var dismissListener: OnDismissListener? = null

    interface OnDismissListener {
        fun onDismiss()
    }

    open fun setOnDismissListener(dismissListener: OnDismissListener?): BaseDialogFragment<*,*> {
        this.dismissListener = dismissListener
        return this
    }
}