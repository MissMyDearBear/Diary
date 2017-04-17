package cjx.com.diary.api;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bear on 2017/4/13.
 */

public interface HttpInterface {
    @GET
    Observable<String>getData();
    @GET(ApiService.baseUrl)
    Call<String>getString();
}
