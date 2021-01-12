package com.tiens.comonlibrary.base.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author: lhc
 * @date: 2021-01-11 14:01
 * @description 多布局Adapter array key对应layoutId  value对应data_variable
 */

public abstract class RecycleViewMultiItemBaseAdapter<T> extends RecyclerView.Adapter<RecycleViewMultiItemBaseAdapter.ViewHolder> {
    protected List<T> mDataList;
    protected SparseArray<Integer> mArray;
    protected OnItemClickListener<T> mListener;

    public RecycleViewMultiItemBaseAdapter(List<T> mDataList, SparseArray<Integer> mArray) {
        this.mArray = mArray;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public RecycleViewMultiItemBaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ViewHolder viewHolder = new ViewHolder(viewDataBinding.getRoot(),viewType);
        viewHolder.setViewDataBinding(viewDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewMultiItemBaseAdapter.ViewHolder holder, final int position) {
        Integer variable = mArray.get(getItemViewType(position));
        ViewDataBinding viewDataBinding = holder.getViewDataBinding();
        viewDataBinding.setVariable(variable, mDataList.get(position));
        viewDataBinding.executePendingBindings();
        handleView(viewDataBinding,holder.getViewType(),position,mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mArray.keyAt(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;
        private int viewType;
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
        }

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }

        public void setViewDataBinding(ViewDataBinding viewDataBinding) {
            this.viewDataBinding = viewDataBinding;
        }

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }
    }

    public void setDatas(List<T> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> dataList) {
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void insertDatas(List<T> dataList) {
        int insertPosition = this.mDataList.size();
        this.mDataList.addAll(insertPosition,dataList);
        notifyItemInserted(insertPosition);
    }

    public void updateData(List<T> dataList, int position) {
        this.mDataList = dataList;
        notifyItemChanged(position);
    }

    public void updateData(T data, int position) {
        this.mDataList.set(position,data);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mDataList.size()- position);
    }


    public abstract void handleView(ViewDataBinding binding, int viewType, int position, T data);

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mListener = listener;
    }
}