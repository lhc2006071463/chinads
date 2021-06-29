package com.tiens.comonlibrary.widget.recyclerview;

import android.util.Log;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    // 最后几个完全可见项的位置（瀑布式布局会出现这种情况）
    private int[] lastCompletelyVisiblePositions;
    // 最后一个完全可见项的位置
    private int lastCompletelyVisibleItemPosition;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // 找到最后一个完全可见项的位置
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            if (lastCompletelyVisiblePositions == null) {
                lastCompletelyVisiblePositions = new int[manager.getSpanCount()];
            }
            manager.findLastCompletelyVisibleItemPositions(lastCompletelyVisiblePositions);
            lastCompletelyVisibleItemPosition = getMaxPosition(lastCompletelyVisiblePositions);
        } else if (layoutManager instanceof GridLayoutManager) {
            lastCompletelyVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else {
            throw new RuntimeException("Unsupported LayoutManager.");
        }

        // 通过比对 最后完全可见项位置 和 总条目数，来判断是否滑动到底部
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        Log.d("Tag",totalItemCount+"======"+lastCompletelyVisibleItemPosition);
        if (visibleItemCount > 0
                && totalItemCount>=((PageRecyclerView) recyclerView).getPageSize()
                && lastCompletelyVisibleItemPosition == totalItemCount - ((PageRecyclerView) recyclerView).getPageSize()/3
                && (lastCompletelyVisibleItemPosition/((PageRecyclerView) recyclerView).getPageSize())+2==((PageRecyclerView) recyclerView).getPage()) {
            onScrollToBottom();
            Log.e("Tag", "scrollToBottom-----");
        }
    }

    private int getMaxPosition(int[] positions) {
        int max = positions[0];
        for (int i = 1; i < positions.length; i++) {
            if (positions[i] > max) {
                max = positions[i];
            }
        }
        return max;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    public void onScrollToBottom() {

    }
}