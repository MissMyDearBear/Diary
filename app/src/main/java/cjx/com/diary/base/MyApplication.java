package cjx.com.diary.base;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import cjx.com.diary.mode.user.DaoMaster;
import cjx.com.diary.mode.user.DaoSession;

/**
 * Created by bear on 2017/4/17.
 */

public class MyApplication extends Application {

    public static MyApplication INSTANCE;
    public static final boolean ENCRYPTED = true;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        DaoMaster.DevOpenHelper help = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "bear-db-encrypted" : "bear-db");
        Database db = ENCRYPTED ? help.getEncryptedWritableDb("admin") : help.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
