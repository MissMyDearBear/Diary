package cjx.com.diary.thirdtools;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by bear on 2017/4/13.
 */

public interface ImageInterface {
    /**
     * 显示url链接对应的图片
     * @param context 上下文
     * @param imageView imageView
     * @param url 链接
     */
    void displayImage(Context context, ImageView imageView, String url);

    /**
     * 显示文件对应的图片
     * @param context 上下文
     * @param imageView imageView
     * @param file 文件
     */
    void displayImage(Context context, ImageView imageView, File file);
}
