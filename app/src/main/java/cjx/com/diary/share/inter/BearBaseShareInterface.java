package cjx.com.diary.share.inter;

import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

/**
 * description:分享接口
 * author: bear .
 * Created date:  2017/5/26.
 */
public interface BearBaseShareInterface {
    /**
     * 初始化分享实例
     */
    void init();

    /**
     * 分享
     * @param title  分享标题
     * @param content  分享内容
     * @param params  元素（url or path）
     * @param type {@link cjx.com.diary.share.entry.BearShareContentType}
     */
    void bearShare(@NotNull String title,String content,String params,int type,BearShareCallBack callBack);



    /**
     * 分享
     * @param bitmap  分享的logo
     * @param title  分享标题
     * @param content  分享内容
     * @param params  元素（url or path）
     * @param type {@link cjx.com.diary.share.entry.BearShareContentType}
     */
    void bearShare(Bitmap bitmap, @NotNull String title, String content, String params, int type,BearShareCallBack callBack);
}
