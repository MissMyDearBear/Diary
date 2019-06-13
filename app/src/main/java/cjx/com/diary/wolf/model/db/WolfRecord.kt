package cjx.com.diary.wolf.model.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.Keep

/**
 * description: 狼人杀每晚记录
 * author: bear .
 * Created date:  2019-06-11.
 */
@Keep
@Entity(tableName = "WolfRecordTable")
class WolfRecord {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @ColumnInfo(name = "position")
    var position: Int = 0 //第几晚
    @ColumnInfo(name = "record")
    var recordStr: String = ""

}