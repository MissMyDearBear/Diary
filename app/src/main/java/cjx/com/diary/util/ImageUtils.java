package cjx.com.diary.util;

import cjx.com.diary.thirdtools.glide.GlideImp;
import cjx.com.diary.thirdtools.ImageInterface;

/**
 * @author: bear
 *
 * @Description: 图片相关工具类
 *
 * @date: 2017/5/10
 */

public class ImageUtils {
    public static ImageInterface getInstance(){
        return new ImageUtils().glideImp;
    }
    private GlideImp glideImp;
    private ImageUtils(){
       glideImp=new GlideImp();
    }
}
