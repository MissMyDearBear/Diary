package cjx.com.diary.db;

import org.greenrobot.greendao.database.Database;

import cjx.com.diary.base.MyApplication;
import cjx.com.diary.mode.diary.DaoMaster;
import cjx.com.diary.mode.diary.DaoSession;


/**
 * Created by bear on 2017/4/25.
 */

public class GreenDao implements DBInterface{
    public static final boolean ENCRYPTED = true;
    private DaoSession mDaoSession;

    private GreenDao() {
        DaoMaster.DevOpenHelper help = new DaoMaster.DevOpenHelper(MyApplication.INSTANCE, ENCRYPTED ? "bear-db-encrypted" : "bear-db");
        Database db = ENCRYPTED ? help.getEncryptedWritableDb("super-secret") : help.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    private static class SingleHolder {
        private static final GreenDao INSTANCE = new GreenDao();
    }

    private static GreenDao getInstance() {
        return SingleHolder.INSTANCE;
    }

    public static DaoSession getDaoSession() {
        return getInstance().mDaoSession;
    }
}
