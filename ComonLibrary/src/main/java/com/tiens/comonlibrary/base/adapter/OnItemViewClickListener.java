package com.tiens.comonlibrary.base.adapter;

public interface OnItemViewClickListener<T> {
    void onItemViewClick(RecyclerViewBaseAdapter.MyViewHolder holder, int position, T t);
}
