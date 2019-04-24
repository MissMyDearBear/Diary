package cjx.com.diary.wolf.model.role

/**
 * description:女巫
 * author: bear .
 * Created date:  2017/7/27.
 */
class Witch : Role() {
    override var roleName: String
        get() = "女巫"
        set(value) {}
    //是否有解药
    override var hasAntidote: Boolean = true
    //是否有毒药
    override var hasPoison: Boolean = true

    var canSave: Boolean = true
    var canPoison: Boolean = true
    var wasSavedNumber: Int = 0
    var wasPoisonNumber: Int = 0

    fun save(position: Int) {
        if (canPoison) {
            wasSavedNumber = position
        }
        canSave = false
        hasAntidote=false
    }

    fun poison(position: Int) {
        if (canPoison) {
            wasPoisonNumber = position
        }
        canPoison = false
        hasPoison=false
    }
}