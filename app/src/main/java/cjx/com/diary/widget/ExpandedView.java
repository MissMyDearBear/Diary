package cjx.com.diary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import cjx.com.diary.R;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * description: 可展开view
 * author: bear .
 * Created date:  2017/10/16.
 */
public class ExpandedView extends LinearLayout {

    private Context mContext;

    /**
     * 根布局
     */
    private LinearLayout rootLl;
    /**
     * 内容
     */
    private TextView contentTv;
    /**
     * 展开按钮
     */
    private TextView expandTv;


    /**
     * 内容字颜色
     */
    private int contentTextColor = 0xFF43464F;
    /**
     * 内容字大小【sp】
     */
    private int contentTextSize = 15;
    /**
     * 内容字颜色
     */
    private int expandedTextColor = 0xFF7a808F;
    /**
     * 内容字大小【sp】
     */
    private int expandedTextSize = 13;

    /**
     * 收起icon
     */
    private int expandedCollapIconRes = R.drawable.icon_content_collap;
    /**
     * 展开icon
     */
    private int expandedExpandIconRes = R.drawable.icon_content_expand;

    /**
     * 显示的行数
     */
    private int expandedLinesCount = 3;

    /**
     * 是否展开
     */
    private boolean isExpand = false;

    /**
     * 展开
     */
    private String expandText = "展开";
    /**
     * 收起
     */
    private String collapText = "收起";
    /**
     * 「居左」
     * 展开收起的位置
     */
    public static final int LEFT = 0x100;
    /**
     * 「居中」
     * 展开收起的位置
     */
    public static final int CENTER = 0x101;
    /**
     * 「居右」
     * 展开收起的位置
     */
    public static final int RIGHT = 0x102;


    public ExpandedView(Context context) {
        this(context, null, 0);
    }

    public ExpandedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_expanded, this);
        rootLl = (LinearLayout) view.findViewById(R.id.ll_root);
        contentTv = (TextView) view.findViewById(R.id.tv_content);
        expandTv = (TextView) view.findViewById(R.id.tv_extend);
        contentTv.setTextSize(COMPLEX_UNIT_SP, contentTextSize);
        contentTv.setTextColor(contentTextColor);
        expandTv.setTextSize(COMPLEX_UNIT_SP, expandedTextSize);
        expandTv.setTextColor(expandedTextColor);
    }

    /**
     * 设置内容
     * @param text          内容
     * @param expandedCount 首次展示的行数
     */
    public void setContentText(@NotNull String text, int expandedCount) {
        setContentText(text,expandedCount,false);
    }

    /**
     * 设置内容
     * @param text          内容
     * @param expandedCount 首次展示的行数
     */
    public void setContentText(@NotNull String text, int expandedCount,boolean isDefaultExpand) {
        expandedLinesCount = expandedCount;
        contentTv.setText(text);
        //延迟计算，防止拉到contentTv的text的count为零
        contentTv.postDelayed(() -> setExpandedView(rootLl, contentTv, expandTv, expandedLinesCount,isDefaultExpand), 50);
    }


    private void setExpandedView(@NotNull View rootView, @NotNull TextView textView, @NotNull TextView unfoldedTv, int expandCount,boolean isDefaultExpand) {
        int tvLines = textView.getLineCount();
        final int startHeight = tvLines * textView.getLineHeight();
        if (tvLines > expandCount) {
            unfoldedTv.setVisibility(VISIBLE);
            textView.setHeight(textView.getLineHeight() * expandCount);
            unfoldedTv.setText(expandText);
            unfoldedTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, expandedExpandIconRes, 0);
            unfoldedTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isExpand = !isExpand;
                    textView.clearAnimation();
                    final int defaultValue;
                    int durationMillis = 200;
                    if (isExpand) {
                        /**
                         * 折叠动画
                         * 从实际高度缩回起始高度
                         */
                        defaultValue = textView.getLineHeight() * tvLines - startHeight;
                        unfoldedTv.setText(collapText);
                        unfoldedTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, expandedCollapIconRes, 0);
                    } else {
                        defaultValue = textView.getLineHeight() * expandCount - startHeight;
                        unfoldedTv.setText(expandText);
                        unfoldedTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, expandedExpandIconRes, 0);
                    }

                    Animation animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            super.applyTransformation(interpolatedTime, t);
                            //根据ImageView旋转动画的百分比来显示textview高度，达到动画效果
                            textView.setHeight((int) (startHeight + defaultValue * interpolatedTime));
                        }
                    };
                    animation.setDuration(durationMillis);
                    textView.startAnimation(animation);
                    rootView.postInvalidate();
                }
            });
            /*
             *  默认是收起的，如果设置了默认展开，则执行展开按钮的点击事件
             */
            if (isDefaultExpand) {
                textView.performClick();
            }
        } else {
            unfoldedTv.setVisibility(GONE);
            textView.setHeight(startHeight);
        }
    }

    public void setExpandedTextGravity(int position) {
        int gravity = -1;
        switch (position) {
            case LEFT:
                gravity = Gravity.LEFT;
                break;
            case CENTER:
                gravity = Gravity.CENTER_HORIZONTAL;
                break;
            case RIGHT:
                gravity = Gravity.RIGHT;
                break;
        }
        if (gravity != -1) {
            LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = gravity;
            expandTv.setLayoutParams(lp);
        }
    }

    public void setContentTextColor(int contentTextColor) {
        this.contentTextColor = contentTextColor;
        contentTv.setTextColor(contentTextColor);
    }

    public void setContentTextSize(int contentTextSize) {
        this.contentTextSize = contentTextSize;
        contentTv.setTextSize(COMPLEX_UNIT_SP, contentTextSize);
    }

    public void setExpandedTextColor(int expandedTextColor) {
        this.expandedTextColor = expandedTextColor;
        expandTv.setTextColor(expandedTextColor);
    }

    public void setExpandedTextSize(int expandedTextSize) {
        this.expandedTextSize = expandedTextSize;
        expandTv.setTextSize(COMPLEX_UNIT_SP, expandedTextSize);
    }

    /**
     * 需在setContentText方法之前调用有用
     *
     * @param expandedCollapIconRes
     */
    public void setExpandedIconRes(int expandedCollapIconRes, int expandedExpandIconRes) {
        this.expandedCollapIconRes = expandedCollapIconRes;
        this.expandedExpandIconRes = expandedExpandIconRes;
    }


    public LinearLayout getRootLl() {
        return rootLl;
    }

    public TextView getContentTv() {
        return contentTv;
    }

    public TextView getExpandTv() {
        return expandTv;
    }
    public boolean isExpand() {
        return isExpand;
    }

    public void setExpandText(String expandText) {
        this.expandText = expandText;
    }

    public void setCollapText(String collapText) {
        this.collapText = collapText;
    }
}
