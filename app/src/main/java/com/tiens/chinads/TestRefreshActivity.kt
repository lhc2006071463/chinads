package com.tiens.chinads

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.tiens.apt_annotation.Login
import com.tiens.chinads.databinding.ActivityTestRefreshBinding
import com.tiens.chinads.res.route.RouterPaths.Main.Companion.TEST_REFRESH_ACTIVITY
import com.tiens.comonlibrary.base.adapter.RecyclerViewBaseAdapter
import com.tiens.comonlibrary.base.ui.BaseVMActivity
import com.tiens.comonlibrary.widget.recyclerview.PageRecyclerView

@Route(path = TEST_REFRESH_ACTIVITY)
class TestRefreshActivity : BaseVMActivity<ActivityTestRefreshBinding,TestRefreshVM>(){
    private val list = mutableListOf<ConferenceBean>()
    private lateinit var adapter: RecyclerViewBaseAdapter<ConferenceBean>
    override fun getLayoutId(): Int {
        return R.layout.activity_test_refresh
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        binding.recyclerView.bindRefreshLayout(binding.refreshLayout)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = object: RecyclerViewBaseAdapter<ConferenceBean>(R.layout.item_refresh,mContext,list) {
            override fun handleView(holder: MyViewHolder, position: Int, t: ConferenceBean?) {
                super.handleView(holder, position, t)
                holder.itemView.findViewById<TextView>(R.id.tv_text).text = t?.coName
            }
        }.apply { adapter = this }
    }


    override fun initData() {
        getData()
    }

    private fun getData() {
        mVM.getData(binding.recyclerView.page)
    }

    override fun initListeners() {
        mVM.successData.observe(this, Observer{
            Log.d("Tag","$binding.recyclerView.page====")
            if(binding.recyclerView.page==1) {
                list.clear()
                list.addAll(it.items)
                adapter.setData(list)
            }else {
                adapter.addDatas(it.items)
            }
            binding.recyclerView.setSuccessStatus()
            finishRefreshOrLoadMore()
        })

        mVM.pageErrorData.observe(this, Observer {
            binding.recyclerView.setFailStatus()
            finishRefreshOrLoadMore()
        })

        binding.recyclerView.mOnScrollListener = object : PageRecyclerView.OnScrollListener {

            override fun refresh() {
                getData()
            }

            override fun loadMore() {
                getData()
            }

        }
    }

    private fun finishRefreshOrLoadMore() {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()
    }
}