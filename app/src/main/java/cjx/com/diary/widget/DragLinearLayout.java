package cjx.com.diary.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import cjx.com.diary.R;
import cjx.com.diary.util.Utils;

/**
 * description:
 * author: bear .
 * Created date:  2017/10/31.
 */
public class DragLinearLayout extends LinearLayout {

    private ViewDragHelper mDragger;

    private Point childOriginPos = new Point();

    private View mView;


    private final int screenHeight = Utils.getScreenHeightOrWidth(Utils.SCREEN_HEIGHT);
    private final int screenWidth = Utils.getScreenHeightOrWidth(Utils.SCREEN_WIDTH);


    public DragLinearLayout(Context context) {
        this(context, null);
    }

    public DragLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initDrag(CallBack callBack) {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                mDragger.settleCapturedViewAt(childOriginPos.x, childOriginPos.y);
                invalidate();
                if (callBack != null) {
                    callBack.onViewRelease(releasedChild, xvel, yvel);
                }

            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
            childOriginPos.x = mView.getLeft();
            childOriginPos.y = mView.getTop();
}

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                if (getChildAt(i).getId() == R.id.iv_photo) {
                    mView = getChildAt(i);
                }
            }
        }

    }

public interface CallBack {
    void onViewRelease(View releasedChild, float xvel, float yvel);
}
}
