package cjx.com.diary.mode.weight

/**
 * description: 体重相关类
 * author: bear .
 * Created date:  2017/7/24.
 */
class BodyWeightBean{
    lateinit var mDate:String
    lateinit var mWeight:String
    fun BodyWeightBean(date:String,weight:String){
        this.mDate=date
        this.mWeight=weight
    }

}