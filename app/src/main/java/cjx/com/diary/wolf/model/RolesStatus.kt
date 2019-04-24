package cjx.com.diary.wolf.model

import cjx.com.diary.wolf.model.role.Hunter
import cjx.com.diary.wolf.model.role.Role
import cjx.com.diary.wolf.model.role.Witch

/**
 * description: 所有角色当前的状态
 * author: bear .
 * Created date:  2019-06-10.
 */
class RolesStatus {
    var curActiveUser = ArrayList<Role>()
    var curActiveWolf = ArrayList<Role>()

    fun getWolfCount(): Int = curActiveWolf.size

    fun getActiveUserCount(): Int = curActiveUser.size

    fun isGameOver(): Boolean {
        val godRoleCount = getActiveUserCount() - getWolfCount()
        if (getWolfCount() >= godRoleCount && !checkKillWolfPossibility()) {
            return true
        }
        return false
    }

    private fun checkKillWolfPossibility(): Boolean {
        curActiveUser.forEach {
            if((it is Witch &&  it.hasPoison)||it is Hunter){
                //如果存在有毒药的女巫，或者是猎人
               return true
            }
        }
        return false
    }
}