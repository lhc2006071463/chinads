package com.tiens.comonlibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;

import com.tiens.comonlibrary.R;
import com.tiens.comonlibrary.util.UIUtil;


/**
 * @author: lhc
 * @date: 2020-03-20 20:21
 * @description 自定义TitleBar
 */

public class TitleBar extends RelativeLayout {
    private Context context;
    private Button leftBtn, rightBtn;
    private ImageView leftIv, rightIv;
    private TextView tvTitle,rightTextView;
    private View lineView;

    private int leftTextColor;
    private String leftText;

    private int rightTextColor;
    private String rightText;
    private float titleTextSize, leftTextSize, rightTextSize;
    private int titleTextColor;
    private String title;
    private OnTopbarClickListener listener;
    private boolean isClickFinish = true;

    public interface OnTopbarClickListener {
        void leftClick();

        void rightClick();
    }

    public void setOnTopbarClickListener(OnTopbarClickListener listener) {
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.TopBar);

        leftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);
        leftText = ta.getString(R.styleable.TopBar_leftText);
        leftTextSize = ta.getDimension(R.styleable.TopBar_leftTextSize, 0);


        rightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, Color.BLACK);
        rightTextSize = ta.getDimension(R.styleable.TopBar_rightTextSize, UIUtil.dip2px(context, 14));
        rightText = ta.getString(R.styleable.TopBar_rightText);

        titleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize, UIUtil.sp2px(context, 17));
        titleTextColor = ta.getColor(R.styleable.TopBar_titleTextcolor, Color.BLACK);
        title = ta.getString(R.styleable.TopBar_Toptitle);

        ta.recycle();

        leftBtn = new Button(context);
        rightBtn = new Button(context);
        leftIv = new ImageView(context);
        rightIv = new ImageView(context);
        tvTitle = new TextView(context);
        rightTextView = new TextView(context);
        lineView = new View(context);

        leftBtn.setTextColor(leftTextColor);
        leftBtn.setGravity(Gravity.CENTER);
        leftBtn.setTextSize(16f);
        leftBtn.setText(leftText);
        leftBtn.setVisibility(GONE);


        if (!TextUtils.isEmpty(rightText)) {
            setrightBtIsVisiable(true);
            rightBtn.setTextColor(rightTextColor);
            rightBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            rightBtn.setTextSize(16f);
            rightBtn.setGravity(Gravity.CENTER);
            rightBtn.setPadding(UIUtil.dip2px(getContext(), 10), UIUtil.dip2px(getContext(), 2)
                    , UIUtil.dip2px(getContext(), 10), UIUtil.dip2px(getContext(), 15));
            rightBtn.setText(rightText);
        } else {
            setrightBtIsVisiable(false);
        }

        tvTitle.setText(title);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.getPaint().setTextSize(titleTextSize);
//        tvTitle.getPaint().setFakeBoldText(true);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setEllipsize(TruncateAt.END);
        tvTitle.setSingleLine(true);
        tvTitle.setMaxEms(13);

        rightTextView.setText(rightText);
        rightTextView.setTextColor(rightTextColor);
        rightTextView.getPaint().setTextSize(rightTextSize);
        rightTextView.setGravity(Gravity.CENTER);
        rightTextView.setEllipsize(TruncateAt.MIDDLE);
        rightTextView.setSingleLine(true);
        rightTextView.setMaxEms(8);
        rightTextView.setPadding(UIUtil.dip2px(getContext(), 10), UIUtil.dip2px(getContext(), 2)
                , UIUtil.dip2px(getContext(), 15), UIUtil.dip2px(getContext(), 2));

        lineView.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));

        /**设置标题栏背景颜色*/
        setBackgroundColor(getResources().getColor(android.R.color.white));

        LayoutParams leftBtnLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        leftBtnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
                RelativeLayout.TRUE);
        addView(leftBtn, leftBtnLayoutParams);


        LayoutParams rightBtnLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rightBtnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
                RelativeLayout.TRUE);
        addView(rightBtn, rightBtnLayoutParams);

        LayoutParams titleLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        titleLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        addView(tvTitle, titleLayoutParams);

        LayoutParams rightTextViewLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rightTextViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
                RelativeLayout.TRUE);
        addView(rightTextView, rightTextViewLayoutParams);


        LayoutParams leftIvParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        leftIvParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
                RelativeLayout.TRUE);
        leftIvParams.addRule(RelativeLayout.CENTER_VERTICAL,
                RelativeLayout.TRUE);
        leftIvParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        leftIvParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        leftIv.setLayoutParams(leftIvParams);
        leftIv.setPadding(UIUtil.dip2px(getContext(), 15), UIUtil.dip2px(getContext(), 10), UIUtil.dip2px(getContext(),
                15), UIUtil.dip2px(getContext(), 10));
        leftIv.setImageResource(R.drawable.ic_back);
        addView(leftIv, leftIvParams);

        //固定宽高

        LayoutParams rightIvParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(rightIv, rightIvParams);

        LayoutParams lineParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UIUtil.dip2px(context,0.5f));
        lineParams.addRule(ALIGN_PARENT_BOTTOM);
        addView(lineView,lineParams);

        leftBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.leftClick();
                }
            }
        });


        leftIv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(context instanceof Activity &&isClickFinish) {
                    ((Activity)context).finish();
                }
                if (listener != null)
                    listener.leftClick();
            }
        });
        rightIv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.rightClick();
            }
        });

        rightBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.rightClick();
            }
        });
        rightTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.rightClick();
            }
        });
    }

    public void setIsClickFinish(boolean isClickFinish) {
        this.isClickFinish = isClickFinish;
    }

    /*
    * 是否隐藏标题的左边按钮  默认是不隐藏  需要隐藏的时候在实现该方法
    *      params
    *           flag  true不隐藏   false隐藏
    * */
    public TitleBar setleftBtIsVisiable(boolean flag) {
        if (flag) {
            leftBtn.setVisibility(View.VISIBLE);
        } else {
            leftBtn.setVisibility(View.GONE);
        }
        return this;
    }

    /*
     * 是否隐藏标题的左边图标  默认是不隐藏  需要隐藏的时候在实现该方法
     *      params
     *           flag  true不隐藏   false隐藏
     * */
    public TitleBar setLeftImageIsVisiable(boolean flag) {
        if (flag) {
            leftIv.setVisibility(View.VISIBLE);
        } else {
            leftIv.setVisibility(View.GONE);
        }
        return this;
    }

    /*
    * 是否隐藏标题的右边按钮  默认是不隐藏  需要隐藏的时候在实现该方法
    *      params
    *           flag  true不隐藏   false隐藏
    * */
    public TitleBar setrightBtIsVisiable(boolean flag) {
        rightBtn.setVisibility(flag? View.VISIBLE: View.GONE);
        return this;
    }
    /**
     * 方法功能描述:设置右侧text
     */
    public TitleBar setRightText(String text) {
        rightTextView.setText(TextUtils.isEmpty(text)?"":text);
        return this;
    }

    public TitleBar setRightTextColor(int color) {
        rightTextView.setTextColor(color);
        return this;
    }

    public TitleBar setRightText(int text) {
        rightTextView.setText(text);
        return this;
    }

    public TitleBar setRightTextBold(boolean isBold) {
        rightTextView.getPaint().setFakeBoldText(isBold);
        return this;
    }

    public String getRightText() {
        return rightTextView.getText().toString();
    }
    /**
     * 方法功能描述:设置右侧text是否可见
     */
    public TitleBar setRightTextVisiable(boolean visiable) {
        rightTextView.setVisibility(visiable? View.VISIBLE: View.GONE);
        return this;
    }
    /*
    * 设置左边标题左边的图片
    *
    * */
    public TitleBar setLeftImage(int m) {
        LayoutParams params = new LayoutParams(LayoutParams
                .WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
                RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL,
                RelativeLayout.TRUE);
        params.width = UIUtil.dip2px(getContext(), 41);
        params.height = UIUtil.dip2px(getContext(), 21);
        leftIv.setLayoutParams(params);
        leftIv.setPadding(UIUtil.dip2px(getContext(), 10), 0, UIUtil.dip2px(getContext(),
                10), UIUtil.dip2px(getContext(), 0));
        leftIv.setImageResource(m);
        return this;
    }

    /*
    * 设置左边标题右边的图片
    *
    * */
    public TitleBar setRightImage(int n) {
        rightIv.setImageResource(n);

        LayoutParams params = new LayoutParams(LayoutParams
                .WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
                RelativeLayout.TRUE);
        rightIv.setLayoutParams(params);
        rightIv.setPadding(UIUtil.dip2px(getContext(), 10), 0, UIUtil.dip2px(getContext(),
                20), 0);
        return this;
    }


    public TitleBar setTitle(String text) {
        tvTitle.setText(text);
        return this;
    }
    public TitleBar setTitle(int title) {
        tvTitle.setText(title);
        return this;
    }

    public void showBottomLine(boolean show) {
        lineView.setVisibility(show? View.VISIBLE : View.GONE);
    }

    public TextView getTitleView() {
        return tvTitle;
    }

    public ImageView getLeftIv() {
        return leftIv;
    }

    public ImageView getRightIv() {
        return rightIv;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
