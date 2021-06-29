package com.tiens.comonlibrary.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.tiens.comonlibrary.application.BaseApplication
import com.tiens.comonlibrary.request.ApiException
import com.tiens.comonlibrary.request.HttpResult
import com.tiens.comonlibrary.request.NetworkResponseListener
import com.tiens.comonlibrary.request.PageResponseListener
import com.tiens.comonlibrary.util.RefreshUtils
import com.tiens.comonlibrary.widget.CommonRefreshLayout
import org.json.JSONObject
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class PageRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {
    val requestMap = mutableMapOf<Int,RequestStatus>()
    var page: Int = 1
    var pageSize: Int = 10
    var hasMoreData = true
        set(value) {
            field = value
            if(this::mRefreshLayout.isInitialized) {
                RefreshUtils.setRefreshFooter(hasMoreData,context,mRefreshLayout)
            }
        }
    private lateinit var mRefreshLayout: CommonRefreshLayout
    init {
        addOnScrollListener(object : RecyclerViewScrollListener() {
            override fun onScrollToBottom() {
                super.onScrollToBottom()
                if(hasMoreData) {
                    if(!requestMap.containsKey(page)) {
                        requestMap[page] = RequestStatus.LOADING
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
        finishRefreshOrLoadMore()
        requestMap[page] = RequestStatus.SUCCESS
        this.page++
    }

    fun setFailStatus() {
        finishRefreshOrLoadMore()
        requestMap[page] = RequestStatus.FAIL
    }

    private fun finishRefreshOrLoadMore() {
        if(mRefreshLayout.isRefreshing)
            mRefreshLayout.finishRefresh()
        else
            mRefreshLayout.finishLoadMore()
    }

    var mOnScrollListener: OnScrollListener? = null

    interface OnScrollListener {
        fun refresh()
        fun loadMore()
    }

}