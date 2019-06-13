package cjx.com.diary.wolf.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * description:
 * author: bear .
 * Created date:  2019-06-12.
 */
@Dao
 interface WolfDao {
    @Query("SELECT * FROM WolfRecordTable WHERE position ==(:day)")
    fun getSomeDayRecords(day: Int): Array<WolfRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(record: WolfRecord)

    @Query("DELETE  FROM WolfRecordTable")
    fun clear()
}