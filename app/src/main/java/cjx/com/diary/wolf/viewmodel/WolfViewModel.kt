package cjx.com.diary.wolf.viewmodel

import android.arch.lifecycle.ViewModel
import cjx.com.diary.R
import cjx.com.diary.base.BaseLiveData
import cjx.com.diary.base.DiaryApplication
import cjx.com.diary.wolf.model.RolesStatus
import cjx.com.diary.wolf.model.db.WolfDataBase
import cjx.com.diary.wolf.model.db.WolfRecord
import cjx.com.diary.wolf.model.role.Role

/**
 * description:
 * author: bear .
 * Created date:  2019-06-10.
 */
class WolfViewModel : ViewModel() {
    val roleStatusLiveData = BaseLiveData<RolesStatus>()
    val roleListLiveData = BaseLiveData<ArrayList<Role>>()
    val curDay = BaseLiveData<Int>()
    private val mResource by lazy {
        DiaryApplication.INSTANCE.resources
    }

    fun productRoleList(count: Int) {
        val roleList = ArrayList<Role>()
        var i = 0
        while (i < count) {
            val role = Role()
            with(role) {
                roleName = "平民"
                isAlive = true
                position = i + 1
            }
            roleList.add(role)
            i++
        }
        roleListLiveData.setValue(roleList)
    }

    fun resetData() {
        curDay.setValue(1)
        WolfDataBase.instance.wolfDao().clear()
    }

    /**
     * @param msg 动作
     * @param position 目标玩家位置
     */
    fun addRecord(msg: String, position: Int) {
        var str = when (msg) {
            "杀" -> mResource.getString(R.string.wolf_record_kill, position)
            "查" -> mResource.getString(R.string.wolf_record_check, position)
            "救" -> mResource.getString(R.string.wolf_record_witch_antidote, position)
            "毒" -> mResource.getString(R.string.wolf_record_witch_poison, position)
            "射杀" -> mResource.getString(R.string.wolf_record_hunter, position)
            "守卫" -> mResource.getString(R.string.wolf_record_guard, position)
            "上警" -> mResource.getString(R.string.wolf_record_police, position)
            "授予警长" -> mResource.getString(R.string.wolf_record_policeman, position)
            else -> ""
        }
        if (!str.isEmpty()) {
            val record = WolfRecord()
            record.position = curDay.value?:1
            record.recordStr = str
            WolfDataBase.instance.wolfDao().insertRecord(record)
        }

    }

}