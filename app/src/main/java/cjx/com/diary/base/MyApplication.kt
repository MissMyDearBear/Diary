package cjx.com.diary.base

import android.app.Application
import android.os.Build
import cjx.com.diary.db.MyOpenHelper
import cjx.com.diary.mode.weight.DaoMaster
import cjx.com.diary.mode.weight.DaoSession
import com.squareup.leakcanary.LeakCanary

/**
 * description:
 * author: bear .
 * Created date:  2019-06-04.
 */
class MyApplication : Application() {
    lateinit var mDaoSession: DaoSession

    override fun onCreate() {
        super.onCreate()
        //内存泄漏检测(5.0之前的手机会crash)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            LeakCanary.install(this)
        }
        INSTANCE = this
        val help = MyOpenHelper(this, "bear-db-encrypted")
        val db = help.getEncryptedWritableDb("admin")
        val master = DaoMaster(db)
        System.out.print("当前数据库版本号-->" + master.schemaVersion)
        mDaoSession = master.newSession()

    }

    fun getDaoSession(): DaoSession = mDaoSession

    companion object {
        lateinit var INSTANCE: MyApplication
    }
}