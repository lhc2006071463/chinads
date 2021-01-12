package com.tiens.chinads.owner.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.tiens.chinads.owner.R
import com.tiens.chinads.owner.databinding.OwnerActivityOwnerBinding
import com.tiens.comonlibrary.base.EmptyVM
import com.tiens.comonlibrary.base.ui.BaseVMActivity
import com.tiens.chinads.res.route.RouterPaths

@Route(path = RouterPaths.Owner.OWNER_ACTIVITY)
class OwnerActivity : BaseVMActivity<OwnerActivityOwnerBinding,EmptyVM>() {
    override fun getLayoutId(): Int {
        return R.layout.owner_activity_owner
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListeners() {
    }
}