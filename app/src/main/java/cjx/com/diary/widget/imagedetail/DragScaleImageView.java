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

    private int lastY;
    private int distance;

    private SparseArray<Integer> sparseArray;

    public void setSparseArray(SparseArray<Integer> sparseArray) {
        this.sparseArray = sparseArray;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                AnimatorSet setDown = new AnimatorSet();
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
                distance = (int) (lastY - getY());
                layout(l, t, r, b);
                ((View) this.getParent()).getBackground().setAlpha(0);
                break;
            case MotionEvent.ACTION_UP:
                //抬起的时候，需要回到上个页面跳转过来的位置，这里使用属性动画化来实现
                Utils.logOut("slip:" + distance);
                comeBackLastPageImageState1(x,y);
//                comeBackLastPageImageState2(x,y);
                break;
        }
        return true;
    }

    private void comeBackLastPageImageState2(int x, int y) {
        if (sparseArray != null && sparseArray.size() > 0) {
            int olderWidth = sparseArray.get(2, 0);
            int olderHeight = sparseArray.get(3, 0);
            ScaleAnimation scaleAnimation = new ScaleAnimation(
                    1f, olderWidth * 1f / getWidth(), 1f,
                    olderHeight * 1f / getHeight());
            scaleAnimation.setDuration(300);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    int toX = sparseArray.get(0);
                    int toY = sparseArray.get(1);
                    TranslateAnimation translateAnimation = new TranslateAnimation(x, toX - getLeft(), y, toY - getTop());
                    translateAnimation.setDuration(1000);
                    translateAnimation.setFillAfter(true);
                    DragScaleImageView.this.startAnimation(translateAnimation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

//            AnimationSet mAnimationSet=new AnimationSet(false);
//            mAnimationSet.addAnimation(scaleAnimation);
//            mAnimationSet.setFillAfter(true);
//            mAnimationSet.addAnimation(translateAnimation);
//            mAnimationSet.setAnimationListener();
            DragScaleImageView.this.startAnimation(scaleAnimation);
        }
    }

    private void comeBackLastPageImageState1(int x,int y) {
        AnimatorSet setUp = new AnimatorSet();
        Animator[] animators = new Animator[4];
        if (sparseArray != null && sparseArray.size() > 0) {
            int olderX = sparseArray.get(0, 0);
            int olderY = sparseArray.get(1, 0);
            int[] axi = new int[2];
            getLocationOnScreen(axi);
            int currX = axi[0];
            int currY = axi[1];
            int olderWidth = sparseArray.get(2, 0);
            int olderHeight = sparseArray.get(3, 0);

            int newWidth = getWidth();
            int newHeight = getHeight();
            float xScale = olderWidth * 1f / newWidth;
            float yScale = olderHeight * 1f / newHeight;

//            animators[2] = ObjectAnimator.ofFloat(this, "translationX", (olderX - currX - getWidth() * 1f / 2) * 0.8f);
//            animators[3] = ObjectAnimator.ofFloat(this, "translationY", (olderY - currY - getHeight() * 1f / 2) * 0.8f);
            animators[2] = ObjectAnimator.ofFloat(this, "translationX", olderX - currX-x*xScale/2);
            animators[3] = ObjectAnimator.ofFloat(this, "translationY", olderY - currY-y*yScale/2);

            animators[0] = ObjectAnimator.ofFloat(this, "scaleX", 1f, xScale);
            animators[1] = ObjectAnimator.ofFloat(this, "scaleY", 1f, yScale);

        }
        setUp.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                        ImageHelper.finishImageShow(appCompatActivity);
                        if (mCallBack != null) {
                            mCallBack.onFinished();
                        }
            }

        });
        setUp.setInterpolator(new DecelerateInterpolator());
//                }
        setUp.playTogether(animators);
        setUp.start();
    }


    public interface CallBack {
        void onFinished();
    }
}
