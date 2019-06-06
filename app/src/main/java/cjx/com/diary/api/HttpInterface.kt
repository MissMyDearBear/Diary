package cjx.com.diary.api


import bear.com.data.repository.db.model.NewsModel
import cjx.com.diary.mode.BaiDuImageBean
import cjx.com.diary.mode.ImagesResult
import cjx.com.diary.mode.test.RoomResult
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author: bear
 *
 * @Description: API接口定义
 *
 * @date: 2017/5/10
 */

interface HttpInterface {

    @get:GET(value = "http://192.168.40.69:1377")
    val qiuBai: Observable<NewsModel>

    @FormUrlEncoded
    @POST("/ModuleDefaultCompany/RentManage/SearchRentNo/")
    fun search(@Field("CertNo") idNo: String): Observable<RoomResult>

    @FormUrlEncoded
    @POST(value = "http://www.tngou.net/tnfs/api/list")
    fun getImages(@Field("page") index: Int, @Field("rows") rows: Int): Observable<ImagesResult>

    @GET(value = "http://image.baidu.com/channel/listjson?rn=10&ie=utf8")
    fun getBaiDuImage(@Query("pn") index: Int, @Query("tag1") tag1: String,
                      @Query("tag2") tag2: String): Observable<BaiDuImageBean>
}
