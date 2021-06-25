package com.tiens.chinads

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiens.chinads.databinding.ActivityTestRefreshBinding
import com.tiens.comonlibrary.base.EmptyVM
import com.tiens.comonlibrary.base.adapter.RecyclerViewBaseAdapter
import com.tiens.comonlibrary.base.ui.BaseVMActivity
import com.tiens.comonlibrary.widget.recyclerview.PageRecyclerView

class TestRefreshActivity : BaseVMActivity<ActivityTestRefreshBinding,EmptyVM>(){
    private val list = mutableListOf<String>()
    private lateinit var adapter: RecyclerViewBaseAdapter<String>
    override fun getLayoutId(): Int {
        return R.layout.activity_test_refresh
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        binding.recyclerView.bindRefreshLayout(binding.refreshLayout)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = object: RecyclerViewBaseAdapter<String>(R.layout.item_refresh,mContext,list) {
            override fun handleView(holder: MyViewHolder, position: Int, t: String?) {
                super.handleView(holder, position, t)
                holder.itemView.findViewById<TextView>(R.id.tv_text).text = t
            }
        }.apply { adapter = this }
    }

    override fun initData() {
        for(i in 0..10) {
            list.add("position $i")
        }
        binding.recyclerView.page++
    }

    override fun initListeners() {
        binding.recyclerView.mOnScrollListener = object : PageRecyclerView.OnScrollListener {

            override fun refresh() {
                list.clear()
                for(i in 0..10) {
                    list.add("position $i")
                }
                binding.recyclerView.setSuccessStatus()
                adapter.setData(list)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.refreshLayout.finishRefresh()
                },1000)
            }

            override fun loadMore() {
                Handler(Looper.getMainLooper()).postDelayed({
                    val subList = mutableListOf<String>()
                    for(i in list.size..list.size+10) {
                        subList.add("position $i")
                    }
                    binding.recyclerView.setSuccessStatus()
                    adapter.addDatas(subList)
                },500)
                Handler(Looper.getMainLooper()).postDelayed({
                    if(binding.refreshLayout.isLoading)
                        binding.refreshLayout.finishLoadMore()
                },200)
            }

        }
    }
}