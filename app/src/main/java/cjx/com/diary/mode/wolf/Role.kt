package cjx.com.diary.mode.wolf

/**
 * description:
 * author: bear .
 * Created date:  2017/7/27.
 */
open class Role {
    //是否还活着
    open var isAlive: Boolean = true
    //是否可以发言
    open var canSpeak: Boolean = true
    //投票的号数
    open var voteNumber: Int = 0
    //玩家序号
    open var position: Int = 0

    open var isGoodMan: Boolean = true

    open var roleName: String = ""
    //死亡类型
    open var dieType: String = ""

    //死亡
    open fun die(die: Int) {
        this.isAlive = false
        this.canSpeak = false
        dieType = getType(die)
    }

    //被救了
    open fun saved() {
        this.isAlive = true
    }

    //投票
    open fun vote(number: Int) {
        this.voteNumber = number
    }

    //分配序号
    open fun assign(p: Int) {
        this.position = p

    }

    companion object {
        val wolfKill = 0
        val poisonKill = 1
        val voteKill = 2
        fun getType(type: Int): String {
            when (type) {
                0 -> return "狼人杀的"
                1 -> return "毒杀"
                2 -> return "投票死的"
            }
            return ""
        }
    }
}