package cjx.com.diary.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bear on 2017/4/13.
 */

public class ApiService {


    //获取单例
    public static HttpInterface getApiService() {
        return getInstance().mHttpInterface;
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final ApiService INSTANCE = new ApiService();
    }

    private static ApiService getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private HttpInterface mHttpInterface;

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build();
        mHttpInterface = retrofit.create(HttpInterface.class);
    }

    private final String BASE_URL="http://192.168.40.241:1377";
}
