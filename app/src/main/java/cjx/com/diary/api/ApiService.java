package cjx.com.diary.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bear on 2017/4/13.
 */

public class ApiService {

    private Retrofit retrofit;

    //获取单例
    public static HttpInterface getInstance(){
        return SingletonHolder.INSTANCE.api;
    }
    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final ApiService INSTANCE = new ApiService();
    }
 private HttpInterface api;
 private ApiService(){
             retrofit=new Retrofit.Builder().
             baseUrl(baseUrl).
             addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
             addConverterFactory(GsonConverterFactory.create()).
             build();
     api=retrofit.create(HttpInterface.class);
 }

 public static final String baseUrl="http://baidu.com";
}
