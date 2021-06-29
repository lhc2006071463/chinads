package com.tiens.comonlibrary.util;

import android.content.Context;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.tiens.comonlibrary.widget.CustomRefreshFooter;
import com.tiens.comonlibrary.widget.RefreshNoMoreFooter;


/**
 * author zhangbw
 * Date: 2020/6/4
 * Description
 */
public class RefreshUtils {
    public static void setRefreshFooter(boolean isHasNext, Context context, SmartRefreshLayout refresh) {
        if (refresh != null) {
            refresh.finishLoadMore();
            refresh.finishRefresh();
            if (isHasNext) {
                refresh.setNoMoreData(false);
                refresh.setRefreshFooter(new CustomRefreshFooter(context));
            } else {
                refresh.finishLoadMoreWithNoMoreData();
                refresh.setRefreshFooter(new RefreshNoMoreFooter(context));
            }
        }
    }
}
