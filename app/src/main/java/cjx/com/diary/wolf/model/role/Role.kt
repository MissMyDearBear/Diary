package cjx.com.diary.wolf.model.role

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
    //是否有解药
    open var hasAntidote: Boolean = false
    //是否有毒药
    open var hasPoison: Boolean = false
    //投票的号数
    open var voteNumber: Int = 0
    //玩家序号
    open var position: Int = 0

    open var isGoodMan: Boolean = true

    open var roleName: String = ""
    //死亡类型
    open var dieType: String = ""
    //是否被查验
    open var isChecked = false
    //是否被杀
    open var isKilled = false
    //是否被守护
    open var isProtected = false
    //是否被救
    open var isSaved = false

    open var isPositioned = false

    open var isHunterKilled = false

    var police: Int = -1// 1。警，2.手


    fun aliveStatus(): Boolean {
        if (isKilled) {
            return isSaved xor isProtected
        }
        if (isPositioned || isHunterKilled) {
            return false
        }
        return true
    }

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