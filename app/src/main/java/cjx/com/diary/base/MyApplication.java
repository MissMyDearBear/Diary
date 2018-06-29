package cjx.com.diary.base;

import android.app.Application;
import android.os.Build;

import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;

import cjx.com.diary.BuildConfig;
import cjx.com.diary.db.MyOpenHelper;
import cjx.com.diary.mode.weight.DaoMaster;
import cjx.com.diary.mode.weight.DaoSession;


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
        //内存泄漏检测(5.0之前的手机会crash)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
        INSTANCE = this;
        DaoMaster.DevOpenHelper help = new MyOpenHelper(this, ENCRYPTED ? "bear-db-encrypted" : "bear-db");
        Database db = ENCRYPTED ? help.getEncryptedWritableDb("admin") : help.getWritableDb();
        DaoMaster master=new DaoMaster(db);
        System.out.print("当前数据库版本号-->"+master.getSchemaVersion());
        mDaoSession = master.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
