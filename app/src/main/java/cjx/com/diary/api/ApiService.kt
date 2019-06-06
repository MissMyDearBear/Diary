package cjx.com.diary.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: bear
 *
 * @Description: HttpServer
 *
 * @date: 2017/5/10
 */

class ApiService private constructor() {

    private val mHttpInterface: HttpInterface

    private val BASE_URL = "http://192.168.40.137:1377"

    //在访问HttpMethods时创建单例
    private object SingletonHolder {
        val INSTANCE = ApiService()
    }

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        mHttpInterface = retrofit.create(HttpInterface::class.java)
    }

    companion object {

        //获取单例
        val apiService: HttpInterface
            get() = instance.mHttpInterface

        private val instance: ApiService
            get() = SingletonHolder.INSTANCE
    }
}
