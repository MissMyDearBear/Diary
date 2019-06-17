package cjx.com.diary.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Locale;
import java.util.UUID;

import cjx.com.diary.base.DiaryApplication;

/**
 * @author: bear
 * @Description: 常用工具类
 * @date: 2017/5/10
 */

public class Utils {
    /**
     * 显示toast提示
     *
     * @param context 上下文
     * @param message 显示的内容
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取随机的ID作为日记的Uid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * log输出
     *
     * @param str
     */
    public static void logOut(String str) {
        System.out.println(str);
    }

    public static final int SCREEN_WIDTH = 0;
    public static final int SCREEN_HEIGHT = 1;

    public static int getScreenHeightOrWidth(int type) {
        int result = -1;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) DiaryApplication.INSTANCE.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        switch (type) {
            case SCREEN_WIDTH:
                result = metrics.widthPixels;
                break;
            case SCREEN_HEIGHT:
                result = metrics.heightPixels;
                break;

        }
        return result;
    }
    public static final int HUNDRED_MILLION=100000000;
    public static final int TEN_THOUSAND=10000;
    public static String formatCount2(long num, String defValue) {
        if (num >= HUNDRED_MILLION) {
            float n = num / (float) HUNDRED_MILLION;
            float remainder = n % 1;
            if (remainder > 0.99 || remainder < 0.01) {
                return StringFormatter.format(Locale.CHINA, "%.0f亿", n);
            } else {
                return StringFormatter.format(Locale.CHINA, "%.2f亿", n);
            }
        } else if (num >= 99999999) {
            // 防止出现10000万
            return "1亿";
        } else if (num >= TEN_THOUSAND) {
            float n = num / (float) TEN_THOUSAND;
            float remainder = n % 1;
            // 14499 -> 1.4万 14999 -> 1.5万 19499 -> 1.9万 19599 -> 2万
            if (remainder > 0.99 || remainder < 0.01) {
                return StringFormatter.format(Locale.CHINA, "%.0fw", n);
            } else {
                return StringFormatter.format(Locale.CHINA, "%.2fw", n);
            }
        } else if (num > 0) {
            return String.valueOf(num);
        } else {
            return defValue;
        }
    }
}
