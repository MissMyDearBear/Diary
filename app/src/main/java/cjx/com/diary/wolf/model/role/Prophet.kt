package cjx.com.diary.wolf.model.role

/**
 * description:预言家
 * author: bear .
 * Created date:  2017/7/27.
 */
class Prophet : Role() {
    override var roleName: String
        get() = "预言家"
        set(value) {}

    var checkPosition: Int = 0
    //查验身份
    fun checkIdPosition(position: Int) {
        checkPosition = position
    }

    companion object {
        const val NAME = "预言家"
    }
}