package bear.com.data.repository.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import bear.com.data.repository.db.UserDao;
import bear.com.data.repository.db.model.UserModel;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/14.
 */
@Database(entities = {UserModel.class}, version = 1, exportSchema = false)
public abstract class DiaryDataBase extends RoomDatabase {
    private static DiaryDataBase INSTANCE;
    private static final Object sLock = new Object();

    public abstract UserDao userDao();

    public static DiaryDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), DiaryDataBase.class, "user.db")
                                .allowMainThreadQueries()
                                .build();
            }
            return INSTANCE;
        }
    }


}
