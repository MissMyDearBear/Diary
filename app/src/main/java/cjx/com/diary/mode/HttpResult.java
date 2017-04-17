package cjx.com.diary.mode;

import android.text.TextUtils;

/**
 * Created by bear on 2017/4/14.
 */

public class HttpResult<T> {
    private   final String SUCCESS="1";
    public String code;
    public String message;
    public T data;
    public  boolean isSuccess(){
        return TextUtils.equals(SUCCESS,code);
    }

}
