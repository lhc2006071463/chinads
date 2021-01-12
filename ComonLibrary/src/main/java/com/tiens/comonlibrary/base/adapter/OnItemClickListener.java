package com.tiens.comonlibrary.base.adapter;

public interface OnItemClickListener<T> {
    void onItemClick(RecyclerViewBaseAdapter.MyViewHolder holder, int position, T t);
}
