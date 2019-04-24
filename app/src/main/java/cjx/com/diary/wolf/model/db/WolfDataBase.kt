package cjx.com.diary.wolf.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import cjx.com.diary.base.DiaryApplication

/**
 * description:
 * author: bear .
 * Created date:  2019-06-11.
 */
@Database(entities = [WolfRecord::class], version = 1, exportSchema = false)
open abstract class WolfDataBase private constructor(context: Context) : RoomDatabase() {

    companion object {
        val instance: WolfDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(DiaryApplication.INSTANCE, WolfDataBase::class.java, "wolf.db")
                    .allowMainThreadQueries()
                    .build()
        }
    }

}