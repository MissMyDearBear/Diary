package cjx.com.diary.util.miscreenshot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.List;

import cjx.com.diary.util.Utils;

/**
 * description:
 * author: bear .
 * Created date:  2018/1/17.
 */
public class ScrollableViewRECUtil {
    public static final int VERTICAL = 0;
    private static final long DELAY = 1;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private int orientation = VERTICAL;
    private View view;
    private boolean isEnd;
    private OnRecFinishedListener listener;

    public ScrollableViewRECUtil(View view, int orientation) {
        this.view = view;
        this.orientation = orientation;
    }

    public void start(final OnRecFinishedListener listener) {
        this.listener = listener;
        final MotionEvent motionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, view.getWidth() / 2, view.getHeight() / 2, 0);
        view.dispatchTouchEvent(motionEvent);
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        motionEvent.setAction(MotionEvent.ACTION_MOVE);
        //滑动距离大于ViewConfiguration.get(view.getContext()).getScaledTouchSlop()时listview才开始滚动
        motionEvent.setLocation(motionEvent.getX(), motionEvent.getY() - (ViewConfiguration.get(view.getContext()).getScaledTouchSlop() + 1));
        view.dispatchTouchEvent(motionEvent);
        motionEvent.setLocation(motionEvent.getX(), view.getHeight() / 2);
        view.postDelayed(new Runnable() {
            double n = 10;//默认偏移量
            int currPage = 0;
            int dy;

            @Override
            public void run() {
                dy = (view.getHeight() / 2 - (int) motionEvent.getY());
                if (dy / view.getHeight() >= 10) {
                    isEnd = true;
                }
                Utils.logOut("偏移量:" + dy + ";" + n + ";viewHeight:" + view.getHeight() + ";" + currPage * view.getHeight());
                if (isEnd) {
                    //停止时正好一屏则全部绘制，否则绘制部分
                    if (dy % view.getHeight() == 0) {
                        Bitmap bitmap = rec();
                        bitmaps.add(bitmap);
                    } else {
                        Bitmap origBitmap = rec();
                        Bitmap bitmap = Bitmap.createBitmap(origBitmap, 0, view.getHeight() - dy % view.getHeight(), view.getWidth(), dy % view.getHeight());
                        bitmaps.add(bitmap);
                        origBitmap.recycle();
                    }
                    //最后一张可能高度不足view的高度
                    int h = view.getHeight() * (bitmaps.size() - 1);
                    Bitmap bitmap = bitmaps.get(bitmaps.size() - 1);
                    h = h + bitmap.getHeight();
                    Bitmap result = Bitmap.createBitmap(view.getWidth(), h, Bitmap.Config.RGB_565);
                    Canvas canvas = new Canvas();
                    canvas.setBitmap(result);
                    for (int i = 0; i < bitmaps.size(); i++) {
                        Bitmap b = bitmaps.get(i);
                        canvas.drawBitmap(b, 0, i * view.getHeight(), null);
                        b.recycle();
                    }
                    listener.onRecFinish(result);
                    return;
                }
                if (dy % view.getHeight() == 0) {
                    n = 10;
                    currPage++;
                    Bitmap bitmap = rec();
                    bitmaps.add(bitmap);
                } else {
                    n = Math.min(n, currPage * view.getHeight() - dy);
                }
                motionEvent.setAction(MotionEvent.ACTION_MOVE);
                //模拟每次向上滑动一个像素，这样可能导致滚动特别慢，实际使用时可以修改该值，但判断是否正好滚动了
                //一屏幕就不能简单的根据 (view.getHeight() / 2 - (int) motionEvent.getY()) % view.getHeight() == 0 来确定了。
                //可以每次滚动n个像素，当发现下次再滚动n像素时就超出一屏幕时可以改变n的值，保证下次滚动后正好是一屏幕，
                //这样就可以根据(view.getHeight() / 2 - (int) motionEvent.getY()) % view.getHeight() == 0来判断要不要截屏了。
                motionEvent.setLocation((int) motionEvent.getX(), (int) (motionEvent.getY() - n));
                view.dispatchTouchEvent(motionEvent);
                if (!ViewCompat.canScrollVertically(view, 1)) {
                    isEnd = true;
                }
                view.postDelayed(this, DELAY);
            }
        }, DELAY);
    }

    public void stop() {
        isEnd = true;
    }

    private Bitmap rec() {
        Bitmap film = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas();
        canvas.setBitmap(film);
        view.draw(canvas);
        return film;
    }

    public interface OnRecFinishedListener {
        void onRecFinish(Bitmap bitmap);
    }

}
