package cjx.com.diary.mode.wolf

import android.text.TextUtils

/**
 * description:回合
 * author: bear .
 * Created date:  2017/7/27.
 */
class Round {
    //第几回合
    var index: Int = 1

    //本回合存活的玩家
    val aliveList: ArrayList<Role> = ArrayList()
    //本回合狼人杀的玩家
    var wolfKillRole: Role? = null
    val wolfList:ArrayList<Role> = ArrayList()
    //狼人杀人
    fun wolfKillNumber(position: Int) {
        if (aliveList.size > 0) {
            for (role: Role in aliveList) {
                //被狼杀了
                if (role.position == position) {
                    wolfKillRole = role
                    role.die(Role.wolfKill)
                    if (role is Hunter) {
                        role.canStartSkills = true
                    }
                    break
                }
            }
            aliveList.remove(wolfKillRole)
            dieList.add(wolfKillRole!!)
        }
    }

    //女巫出场
    fun witchSave() {
        wolfKillRole!!.saved()
        dieList.remove(wolfKillRole!!)
        aliveList.add(wolfKillRole!!)
    }

    fun witchPoison(position: Int) {
        if (aliveList.size > 0) {
            for (role: Role in aliveList) {
                //被毒杀了
                if (role.position == position) {
                    wolfKillRole = role
                    role.die(Role.poisonKill)
                    if (role is Hunter) {
                        role.canStartSkills = false
                    }
                    break
                }
            }
            aliveList.remove(wolfKillRole)
            dieList.add(wolfKillRole!!)
        }

    }

    //本回合死亡的玩家
    val dieList: ArrayList<Role> = ArrayList()

    //预言家查询玩家身份
    fun checkRole(position: Int): Boolean {
        var isGoodMan = true
        if (aliveList.size > 0) {
            for (role: Role in aliveList) {
                if (role.position == position) {
                    isGoodMan = role.isGoodMan
                    break
                }
            }
        }
        return isGoodMan
    }

    //是否是平安夜
    fun isPlaceNight(): Boolean{
        return dieList.size==0
    }

     //本回合总结
    fun sumUp():String{
        var content:StringBuilder= StringBuilder("")
         if(isPlaceNight()){
             content.append("昨晚是平安夜")
         }else{
            for(role:Role in dieList){
                content.append("昨天死的是").append(role.position)
                        .append("号玩家")

                if(role is Hunter){
                    if(role.canStartSkills){
                        content.append("请问你需要发动技能吗？")
                    }
                }
                if(role.canSpeak){
                    content.append("请留遗言")
                }else{
                    content.append("没有遗言")
                }
            }
         }
        return content.toString()
    }
    //投票
    fun voteRole(position: Int){
        if (aliveList.size > 0) {
            for (role: Role in aliveList) {
                //被投票了
                if (role.position == position) {
                    wolfKillRole = role
                    role.die(Role.voteKill)
                    if (role is Hunter) {
                        role.canStartSkills = true
                    }
                    break
                }
            }
            aliveList.remove(wolfKillRole)
            dieList.add(wolfKillRole!!)
        }
    }
    //下一夜
    fun nextRound(){
        index++
        dieList.clear()
        wolfKillRole=null
        wolfList.clear()
        if (aliveList.size > 0) {
            for (role: Role in aliveList) {
                if (TextUtils.equals(role.roleName,"狼人")) {
                    wolfList.add(role)
                }
            }
        }

    }
}