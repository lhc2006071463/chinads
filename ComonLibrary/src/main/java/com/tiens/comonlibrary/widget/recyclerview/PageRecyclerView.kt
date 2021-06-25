package com.tiens.comonlibrary.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.tiens.comonlibrary.widget.CommonRefreshLayout

class PageRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

    val requestMap = mutableMapOf<Int,RequestStatus>()
    var page: Int = 1
    var pageSize: Int = 10
    private lateinit var mRefreshLayout: CommonRefreshLayout

    init {
        addOnScrollListener(object : RecyclerViewScrollListener() {
            override fun onScrollToBottom() {
                super.onScrollToBottom()
                if(mRefreshLayout.hasMoreData) {
                    if(!requestMap.containsKey(page)) {
                        requestMap[page] = RequestStatus.LOADING
                        requestMap[page-1] = RequestStatus.SUCCESS
                        mOnScrollListener?.loadMore()
                    }
                }
            }
        })
    }

    fun bindRefreshLayout(refreshLayout: CommonRefreshLayout) {
        this.mRefreshLayout = refreshLayout
        mRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                if(!requestMap.containsKey(page)) {
                    requestMap[page] = RequestStatus.LOADING
                    requestMap[page-1] = RequestStatus.SUCCESS
                    mOnScrollListener?.loadMore()
                } else if(requestMap[page]==RequestStatus.FAIL){
                    requestMap[page] = RequestStatus.LOADING
                    mOnScrollListener?.loadMore()
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                requestMap[page] = RequestStatus.LOADING
                mOnScrollListener?.refresh()
                requestMap.clear()
            }

        })
    }

    fun setSuccessStatus() {
        requestMap[page] = RequestStatus.SUCCESS
        this.page++
    }

    fun setFailStatus() {
        requestMap[page] = RequestStatus.FAIL
    }


    var mOnScrollListener: OnScrollListener? = null

    interface OnScrollListener {
        fun refresh()
        fun loadMore()
    }

}