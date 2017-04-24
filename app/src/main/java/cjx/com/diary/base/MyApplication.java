package cjx.com.diary.base;

import android.app.Application;

/**
 * Created by bear on 2017/4/17.
 */

public class MyApplication extends Application {
    public static MyApplication INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;

    }

}
