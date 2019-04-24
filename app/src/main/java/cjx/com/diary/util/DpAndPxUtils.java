package cjx.com.diary.util;

import cjx.com.diary.base.DiaryApplication;

/**
 * description:
 * author: bear .
 * Created date:  2017/5/18.
 */
public class DpAndPxUtils {
    public static int dip2px(float dpValue) {
        return (int) ((dpValue * DiaryApplication.INSTANCE.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return (int) ((pxValue / DiaryApplication.INSTANCE.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getScreenHeightPixels() {
        return DiaryApplication.INSTANCE.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidthPixels() {
        return DiaryApplication.INSTANCE.getResources().getDisplayMetrics().widthPixels;
    }
}
