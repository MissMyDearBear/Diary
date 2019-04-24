package cjx.com.diary.wolf.viewmodel

import android.arch.lifecycle.ViewModel
import cjx.com.diary.base.BaseLiveData
import cjx.com.diary.wolf.model.RolesStatus
import cjx.com.diary.wolf.model.role.Role

/**
 * description:
 * author: bear .
 * Created date:  2019-06-10.
 */
class WolfViewModel : ViewModel() {
    val mRoleStatusLiveData = BaseLiveData<RolesStatus>()
    val mRoleListLiveData = BaseLiveData<ArrayList<Role>>()


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
        mRoleListLiveData.setValue(roleList)
    }

}