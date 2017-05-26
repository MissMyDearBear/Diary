package cjx.com.diary.share.inter;

/**
 * description:
 * author: bear .
 * Created date:  2017/5/26.
 */
public interface BearShareCallBack {
    /**
     * 分享成功后回调
     *
     * @param plat    分享平台{@link cjx.com.diary.share.entry.BearSharePlatformType}
     * @param content 分享类型{@link cjx.com.diary.share.entry.BearShareContentType}
     * @param other   其它
     */
    void onSuccessed(int plat, int content, String other);

    /**
     * 分享失败后回调
     *
     * @param plat    分享平台{@link cjx.com.diary.share.entry.BearSharePlatformType}
     * @param content 分享类型{@link cjx.com.diary.share.entry.BearShareContentType}
     * @param msg     错误信息
     */
    void onError(int plat, int content, String msg);
}
