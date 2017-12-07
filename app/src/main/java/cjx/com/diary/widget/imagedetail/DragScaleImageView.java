package cjx.com.diary.widget.imagedetail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import cjx.com.diary.util.Utils;

/**
 * description:
 * author: bear .
 * Created date:  2017/11/28.
 */
public class DragScaleImageView extends android.support.v7.widget.AppCompatImageView {
    public DragScaleImageView(Context context) {
        super(context);
        appCompatActivity = (AppCompatActivity) context;
        init();
    }

    public DragScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        appCompatActivity = (AppCompatActivity) context;
        init();
    }

    public DragScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        appCompatActivity = (AppCompatActivity) context;
        init();
    }

    private AppCompatActivity appCompatActivity;

    private void init() {
    }

    private CallBack mCallBack;

    public void setmCallBack(CallBack mC) {
        this.mCallBack = mC;

    }

    private SparseArray<Integer> sparseArray;

    public void setSparseArray(SparseArray<Integer> sparseArray) {
        this.sparseArray = sparseArray;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int x = (int) event.getX();
        int y = (int) event.getY();
        int downX = 0, upX = 0, downY = 0, upY = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) getX();
                downY = (int) getY();
                AnimatorSet setDown = new AnimatorSet();
                setDown.setDuration(50);
                setDown.playTogether(
                        ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.8f),
                        ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.8f)
                );
                setDown.start();
                break;
            case MotionEvent.ACTION_MOVE:
                //滑动时，先将触摸点设置为view的中心点
                int l = (int) (x - getWidth() / 2 + getTranslationX() + getLeft());
                int t = (int) (y - getHeight() / 2 + getTranslationY() + getTop());
                int r = l + getWidth();
                int b = t + getHeight();
                layout(l, t, r, b);
                ((View) this.getParent()).getBackground().setAlpha(0);
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) getX();
                upY = (int) getY();
                int distance = Math.max(Math.abs(upX - downX), Math.abs(upY - downY));
                if (distance < 5) {
                    dismissImage();
                } else {
                    //抬起的时候，需要回到上个页面跳转过来的位置，这里使用属性动画化来实现
                    comeBackLastPageImageState();
                }

                break;
        }
        return true;
    }

    private void comeBackLastPageImageState() {
        AnimatorSet setUp = new AnimatorSet();
        if (sparseArray != null && sparseArray.size() > 0) {

            int olderWidth = sparseArray.get(2, 0);
            int olderHeight = sparseArray.get(3, 0);

            int newWidth = getWidth();
            int newHeight = getHeight();
            float xScale = olderWidth * 1f / newWidth;
            float yScale = olderHeight * 1f / newHeight;
            Animator animator3 = ObjectAnimator.ofFloat(DragScaleImageView.this, "scaleX", 1f, xScale);
            Animator animator4 = ObjectAnimator.ofFloat(DragScaleImageView.this, "scaleY", 1f, yScale);
            setUp.playTogether(animator3, animator4);


            setUp.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    int olderX = sparseArray.get(0, 0);
                    int olderY = sparseArray.get(1, 0);
                    int[] axi = new int[2];
                    getLocationOnScreen(axi);
                    int currX = axi[0];
                    int currY = axi[1];
                    AnimatorSet set = new AnimatorSet();
                    Animator animator1 = ObjectAnimator.ofFloat(DragScaleImageView.this, "translationX", olderX - currX);
                    Animator animator2 = ObjectAnimator.ofFloat(DragScaleImageView.this, "translationY", olderY - currY);
                    set.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            dismissImage();
                        }

                    });
                    set.playTogether(animator1, animator2);
                    set.setDuration(200);
                    set.setInterpolator(new DecelerateInterpolator());
                    set.start();
                }

            });
            setUp.setInterpolator(new DecelerateInterpolator());
            setUp.setDuration(100);
            setUp.start();
        }
    }

    public void dismissImage() {
        ImageHelper.finishImageShow(appCompatActivity);
        if (mCallBack != null) {
            mCallBack.onFinished();
        }
    }

    public interface CallBack {
        void onFinished();
    }
}
