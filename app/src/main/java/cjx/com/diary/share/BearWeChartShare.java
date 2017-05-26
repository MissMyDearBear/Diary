package cjx.com.diary.share;

import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import cjx.com.diary.share.inter.BearBaseShareInterface;
import cjx.com.diary.share.inter.BearShareCallBack;

/**
 * description: 微信分享
 * author: bear .
 * Created date:  2017/5/26.
 */
public class BearWeChartShare implements BearBaseShareInterface {
    @Override
    public void init() {

    }

    @Override
    public void bearShare(@NotNull String title, String content, String params, int type, BearShareCallBack callBack) {

    }

    @Override
    public void bearShare(Bitmap bitmap, @NotNull String title, String content, String params, int type, BearShareCallBack callBack) {

    }

}
