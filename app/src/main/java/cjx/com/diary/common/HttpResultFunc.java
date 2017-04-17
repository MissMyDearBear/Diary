package cjx.com.diary.common;

import cjx.com.diary.mode.ApiParseException;
import cjx.com.diary.mode.HttpResult;
import rx.functions.Func1;

/**
 * Created by bear on 2017/4/17.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (null != tHttpResult) {
            if (tHttpResult.isSuccess()) {
                return tHttpResult.data;
            } else {
                throw new ApiParseException(tHttpResult.code, tHttpResult.message);
            }
        }
        return null;
    }
}
