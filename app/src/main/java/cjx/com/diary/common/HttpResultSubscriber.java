package cjx.com.diary.common;

/**
 * Created by bear on 2017/4/24.
 */

public abstract class HttpResultSubscriber<T>  {


    public abstract void onSuccess(T t);

    public abstract void onSurprise(String msg);
}
