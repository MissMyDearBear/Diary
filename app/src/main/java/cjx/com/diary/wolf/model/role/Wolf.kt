package cjx.com.diary.wolf.model.role

/**
 * description: 狼人
 * author: bear .
 * Created date:  2017/7/27.
 */
class Wolf : Role() {
    override var roleName: String
        get() = "狼人"
        set(value) {}
    override var isGoodMan: Boolean
        get() = false
        set(value) {}

    var killPosition: Int = 0
    //击杀
    fun kill(position: Int) {
        killPosition = position
    }
}