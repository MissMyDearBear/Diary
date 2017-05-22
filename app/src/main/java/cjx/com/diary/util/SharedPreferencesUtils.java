package cjx.com.diary.util;

import android.content.Context;
import android.content.SharedPreferences;

import cjx.com.diary.base.MyApplication;

/**
 * description:sp 工具类
 * author: bear .
 * Created date:  2017/5/22.
 */
public class SharedPreferencesUtils {
    private static final String SP_SPLASH="sp_splash";

    private static SharedPreferences getSp(String spName){
        return MyApplication.INSTANCE.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * @return splash相关的sp
     */
    public static SharedPreferences getSplashSp(){
        return getSp(SP_SPLASH);
    }
}
