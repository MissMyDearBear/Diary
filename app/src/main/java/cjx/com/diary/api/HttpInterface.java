package cjx.com.diary.api;


import cjx.com.diary.mode.ImagesResult;
import cjx.com.diary.mode.QiuBaiBean;
import cjx.com.diary.mode.test.RoomResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author: bear
 *
 * @Description: API接口定义
 *
 * @date: 2017/5/10
 */

public interface HttpInterface {
    @FormUrlEncoded
    @POST("/ModuleDefaultCompany/RentManage/SearchRentNo/")
    Observable<RoomResult> search(@Field("CertNo") String idNo);

    @FormUrlEncoded
    @POST(value = "http://www.tngou.net/tnfs/api/list")
    Observable<ImagesResult>getImages(@Field("id")int id,@Field("page")int index,@Field("rows")int rows);

    @GET(value = "http://192.168.40.69:1377")
    Observable<QiuBaiBean>getQiuBai();
}
