package cjx.com.diary.api;


import java.util.List;

import cjx.com.diary.mode.test.RoomResult;
import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by bear on 2017/4/13.
 */

public interface HttpInterface {
    @FormUrlEncoded
    @POST("/ModuleDefaultCompany/RentManage/SearchRentNo/")
    Observable<RoomResult> search(@Field("CertNo") String idNo);

    @GET(value = "http://192.168.40.137:1377")
    Observable<List<String>>getImages();
}
