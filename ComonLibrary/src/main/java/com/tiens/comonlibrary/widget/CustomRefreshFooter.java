package com.tiens.comonlibrary.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.scwang.smart.drawable.ProgressDrawable;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.tiens.comonlibrary.R;
import com.tiens.comonlibrary.util.UIUtil;

/**
 * author zhangbw
 * Date: 2018/10/25
 * Description
 */
public class CustomRefreshFooter extends LinearLayout implements RefreshFooter {
    private TextView mHeaderText;//标题文本
    private ProgressDrawable mProgressDrawable;//刷新动画

    public CustomRefreshFooter(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        mHeaderText = new TextView(context);
        mProgressDrawable = new ProgressDrawable();
        mHeaderText.setTextColor(getResources().getColor(R.color.text_21));
        mHeaderText.setText(getResources().getString(R.string.pt_common_loadmore));
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(UIUtil.dip2px(context,48));
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
//        mProgressDrawable.start();//开始动画
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
//        mProgressDrawable.stop();//停止动画
        if (success) {
            mHeaderText.setText(getResources().getString(R.string.pt_common_loadmore));
        } else {
            mHeaderText.setText(getResources().getString(R.string.pt_common_loadmore));
        }
        return 500;//延迟500毫秒之后再弹回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }
}
