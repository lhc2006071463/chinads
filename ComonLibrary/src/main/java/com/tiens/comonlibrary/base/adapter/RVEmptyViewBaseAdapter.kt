package com.tiens.comonlibrary.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class RVEmptyViewBaseAdapter<T> : RecyclerViewBaseAdapter<T> {
    var mEmptyView: View? = null
    companion object {
        const val EMPTY_TYPE: Int = 0
        const val CONTENT_TYPE: Int = 1
    }
    constructor(layoutId: Int, variable: Int, datas: MutableList<T>?):super(layoutId,variable,datas) {
        this.layoutId = layoutId
        this.variable = variable
        this.datas = datas
    }

    constructor(layoutId: Int, context: Context?, datas: MutableList<T>?):super(layoutId,context,datas) {
        this.layoutId = layoutId
        this.context = context
        this.datas = datas
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return if(viewType== EMPTY_TYPE) {
            mEmptyView = getEmptyView(parent.context)
            mEmptyView?.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            MyViewHolder(mEmptyView)
        }else {
            val viewHolder = MyViewHolder(viewDataBinding.root)
            viewHolder.viewDataBinding = viewDataBinding
            viewHolder
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(!isEmptyViewPosition(position)) {
            val binding = holder.viewDataBinding
            binding?.apply {
                setVariable(variable, datas?.get(position))
                executePendingBindings()

                listener?.run {
                    root.setOnClickListener {
                        itemClick(holder, position, datas!![position])
                        onItemClick(holder, position, datas!![position])
                    }
                }

                handleView(holder, position, datas?.get(position))
            }

        }

    }

    override fun getItemCount(): Int {
        val contentSize = if (datas == null) 0 else datas?.size ?: 0
        return if (contentSize>0) contentSize else 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(isEmptyViewPosition(position)) EMPTY_TYPE else CONTENT_TYPE
    }

    private fun isEmptyViewPosition(position: Int): Boolean {
        val contentSize = if (datas == null) 0 else datas?.size ?: 0
        return position==0&&contentSize==0
    }

    fun getEmptyView(context: Context): View? {
        return mEmptyView
    }

    fun setEmptyView(emptyView: View) {
        this.mEmptyView = emptyView
    }


}