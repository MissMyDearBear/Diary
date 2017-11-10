package cjx.com.diary.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.UUID;

import cjx.com.diary.base.MyApplication;

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
        WindowManager wm = (WindowManager) MyApplication.INSTANCE.getSystemService(Context.WINDOW_SERVICE);
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
}
