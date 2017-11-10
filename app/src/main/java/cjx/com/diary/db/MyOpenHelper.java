package cjx.com.diary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import cjx.com.diary.mode.weight.DaoMaster;


/**
 * description: GreenDao帮助类
 * author: bear .
 * Created date:  2017/5/17.
 */
public class MyOpenHelper extends DaoMaster.DevOpenHelper {


    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
       /*此处不用super，因为父类中包含了
       dropAllTables(db, true);
        onCreate(db);
        需要自己定制升级
        */
//       MigrationHelper.getInstance().dropAndCreate(db);
    }
}
