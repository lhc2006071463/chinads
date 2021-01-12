package com.tiens.comonlibrary.widget

import android.content.Context
import android.util.AttributeSet
import com.scwang.smart.refresh.layout.SmartRefreshLayout

open class CommonRefreshLayout : SmartRefreshLayout {
    constructor(mContext: Context) : super(mContext) {}

    constructor(mContext: Context, mAttributeSet: AttributeSet) : super(mContext, mAttributeSet) {}
}