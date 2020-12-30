package com.tiens.comonlibrary.base.ui

import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.tiens.comonlibrary.R
import com.tiens.comonlibrary.base.EmptyVM
import com.tiens.comonlibrary.databinding.FragmentCommonAlertBinding
import com.tiens.comonlibrary.util.UIUtil

class CommonAlertFragment : BaseDialogFragment<FragmentCommonAlertBinding, EmptyVM>() {
    private var titleContent: String? = null
    private var hintContent: String? = null
    private var cancelContent: String? = null
    private var confirmContent: String? = null
    private var hintContentColor = 0
    private var cancelContentColor = 0
    private var confirmContentColor = 0
    private var cancelableOnTouchOutside = true
    private var isShowCancelButton = true

    override fun getLayoutId(): Int {
        return R.layout.fragment_common_alert
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    override fun getWidth(): Int {
        return if (context != null) UIUtil.getScreenWidth(context) - UIUtil.dip2px(context, 60f) else ViewGroup.LayoutParams.WRAP_CONTENT
    }

    override fun canceledOnTouchOutside(): Boolean {
        return cancelableOnTouchOutside
    }

    override fun initListener() {
        binding.tvCancel.setOnClickListener {
            listener?.onViewClick(R.id.tv_cancel)
            dismiss()
        }
        binding.tvConfirm.setOnClickListener {
            listener?.onViewClick(R.id.tv_confirm)
            dismiss()
        }
    }

    override fun initTitleBar() {}
    override fun initData() {
        binding.tvCancel.visibility = (if (isShowCancelButton) View.VISIBLE else View.GONE)
        binding.line2.visibility = (if (isShowCancelButton) View.VISIBLE else View.GONE)
        if (!TextUtils.isEmpty(titleContent)) binding.tvWarning.text = titleContent
        if (!TextUtils.isEmpty(hintContent)) binding.tvHint.text = hintContent
        if (!TextUtils.isEmpty(cancelContent)) binding.tvCancel.text = cancelContent
        if (!TextUtils.isEmpty(confirmContent)) binding.tvConfirm.text = confirmContent
        if (hintContentColor != 0) binding.tvHint.setTextColor(hintContentColor)
        if (cancelContentColor != 0) binding.tvCancel.setTextColor(cancelContentColor)
        if (confirmContentColor != 0) binding.tvConfirm.setTextColor(confirmContentColor)
    }

    fun setTitleContent(titleContent: String?): CommonAlertFragment {
        this.titleContent = titleContent
        return this
    }

    fun setHintContent(hint: String?): CommonAlertFragment {
        hintContent = hint
        return this
    }

    fun setHintContentColor(color: Int): CommonAlertFragment {
        hintContentColor = color
        return this
    }

    fun setCancelButtonText(cancelContent: String?): CommonAlertFragment {
        this.cancelContent = cancelContent
        return this
    }

    fun setCancelButtonColor(color: Int): CommonAlertFragment {
        cancelContentColor = color
        return this
    }

    fun setConfirmButtonText(confirmContent: String?): CommonAlertFragment {
        this.confirmContent = confirmContent
        return this
    }

    fun setConfirmButtonColor(color: Int): CommonAlertFragment {
        confirmContentColor = color
        return this
    }

    fun setCancelableOnTouchOutside(cancelableOnTouchOutside: Boolean): CommonAlertFragment {
        this.cancelableOnTouchOutside = cancelableOnTouchOutside
        return this
    }

    fun showCancelButton(isShowCancelButton: Boolean): CommonAlertFragment {
        this.isShowCancelButton = isShowCancelButton
        return this
    }

    override fun setOnViewClickListener(listener: OnViewClickListener?): CommonAlertFragment {
        this.listener = listener
        return this
    }

    override fun setOnDismissListener(dismissListener: OnDismissListener?): CommonAlertFragment {
        this.dismissListener = dismissListener
        return this
    }

}