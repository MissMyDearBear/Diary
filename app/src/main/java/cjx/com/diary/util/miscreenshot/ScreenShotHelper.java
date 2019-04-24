package cjx.com.diary.util.miscreenshot;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

import cjx.com.diary.util.Utils;
import cjx.com.diary.util.screen.BitmapUtils;

/**
 * description: 仿小米截屏帮助类
 * author: bear .
 * Created date:  2018/1/16.
 */
public class ScreenShotHelper {
    private static final int DEVICE_HEIGHT = Utils.getScreenHeightOrWidth(Utils.SCREEN_HEIGHT);


    public static void screenShot(Activity activity, View scrollView, BitmapUtils.BitmapAndFileCallBack callBack) {
        if (scrollView != null) {
            ScrollableViewRECUtil scrollableViewRECUtil = new ScrollableViewRECUtil(scrollView, ScrollableViewRECUtil.VERTICAL);
            scrollableViewRECUtil.start(bitmap -> BitmapUtils.savingBitmapIntoFile(activity, bitmap, callBack));
        }
    }


    //遍历View树，判断是否能往下滚且可见内容大于半个屏幕
    private static boolean viewCanScroll(View view) {
        boolean canScroll = false;
        //获取在当前屏幕下可见区域
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        int viewHeight = rect.bottom - rect.top;//可见区域的高度
        if (view != null && viewHeight > 0 && ViewCompat.canScrollVertically(view, 1) && viewHeight > (DEVICE_HEIGHT / 2)) {
            canScroll = true;
        }
        return canScroll;
    }

    private static View mScrollView;

    public static View getCanScrollView(ViewGroup rootView) {
        traverseView(rootView);
        return mScrollView;
    }

    private static void traverseView(ViewGroup viewGroup) {
        if (viewGroup != null && viewGroup.getChildCount() > 0) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (viewCanScroll(child)) {
                    mScrollView = child;
                    break;
                }
                if (child instanceof ViewGroup) {
                    traverseView((ViewGroup) child);
                }
            }
        }
    }

}
