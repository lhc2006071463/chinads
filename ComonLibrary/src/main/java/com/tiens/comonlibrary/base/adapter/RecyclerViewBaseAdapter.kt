package com.tiens.comonlibrary.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tiens.comonlibrary.base.adapter.RecyclerViewBaseAdapter.MyViewHolder

class RecyclerViewBaseAdapter<T> : RecyclerView.Adapter<MyViewHolder> {
    var layoutId: Int
    private var variable = 0
    var context: Context? = null
    var datas: MutableList<T>? = null
    var listener: OnItemClickListener<T>? = null
    var recyclerView: RecyclerView? = null

    constructor(layoutId: Int, variable: Int, datas: MutableList<T>?) {
        this.layoutId = layoutId
        this.variable = variable
        this.datas = datas
    }

    constructor(
        layoutId: Int,
        context: Context?,
        datas: MutableList<T>?
    ) {
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
        val viewHolder = MyViewHolder(viewDataBinding.root)
        viewHolder.viewDataBinding = viewDataBinding
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = holder.viewDataBinding
        binding?.apply {
            setVariable(variable, datas?.get(position))
            executePendingBindings()
            root.setOnClickListener {
                itemClick(holder, position, datas!![position])
                listener?.onItemClick(holder, position, datas!![position])
            }
            handleView(holder, position, datas?.get(position))
        }

    }

    override fun getItemCount(): Int {
        return if (datas == null) 0 else datas?.size?:0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun itemClick(holder: MyViewHolder?, position: Int, t: T) {}

    class MyViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        var viewDataBinding: ViewDataBinding? = null

    }

    fun setData(datas: ArrayList<T>?) {
        this.datas = datas
        notifyDataSetChanged()
    }

    fun addDatas(datas: List<T>?) {
        datas?.let {
            this.datas?.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun insertDatas(datas: List<T>?) {
        datas?.let {
            val insertPosition = this.datas?.size?:0
            this.datas!!.addAll(insertPosition, it)
            notifyItemInserted(insertPosition)
        }
    }

    fun updateData(datas: MutableList<T>?, position: Int) {
        datas?.let {
            this.datas = it
            notifyItemChanged(position)
        }
    }

    fun updateData(data: T, position: Int) {
        data?.let {
            datas!![position] = data
            notifyItemChanged(position)
        }
    }

    fun removeItem(position: Int) {
        datas?.let {
            it.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, it.size - position)
        }
    }

    fun handleView(holder: MyViewHolder?, position: Int, t: T?) {}

    fun setOnItemClickListener(listener: OnItemClickListener<T>?) {
        this.listener = listener
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}