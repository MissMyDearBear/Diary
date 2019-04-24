package cjx.com.diary.wolf.model.role

/**
 * description:猎人
 * author: bear .
 * Created date:  2017/7/27.
 */
class Hunter: Role(){
    override var roleName: String
        get() = "猎人"
        set(value) {}
    var canStartSkills:Boolean=false

    var killNumber:Int=0
    fun kill(position: Int){
        killNumber=position
    }

}