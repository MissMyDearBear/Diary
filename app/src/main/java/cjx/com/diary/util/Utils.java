package cjx.com.diary.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bear on 2017/4/13.
 */

public class Utils {
    /**
     * 显示toast提示
     * @param context 上下文
     * @param message 显示的内容
     */
    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
