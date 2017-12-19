package cjx.com.diary.util.screen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * description: 截屏工具
 * author: bear .
 * Created date:  2017/12/19.
 */
public class ScreenShotUtils {

    public static void saveScreen(Activity context, ScreenView screenView, BitmapUtils.BitmapAndFileCallBack callBack) {
        if(context==null||context.isFinishing()||screenView==null) return;
        Bitmap bitmap=screenView.getBitmap();
        if(bitmap==null){
            if(callBack!=null){
                callBack.onResult(null,"");
                return;
            }
        }
        BitmapUtils.savingBitmapIntoFile(context,bitmap,callBack);
    }

    /**
     * 返回屏幕的像素高度或者宽度
     * <p>
     * Util.SCREEN_HIGHT 获取高度 Util.SCREEN_WIDTH 获取宽度
     */
    public static int SCREEN_HIGHT = 1;
    public static int SCREEN_WIDTH = 10;

    public static int getScreenHightOrWidth(int type, Context context) {
        int result = -1;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
        }
        if (type == SCREEN_HIGHT) {
            result = metrics.heightPixels;
        } else if (type == SCREEN_WIDTH) {
            result = metrics.widthPixels;
        }
        return result;
    }
}
