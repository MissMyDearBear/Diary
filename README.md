## 前言
看到网上非常火热的retrofit+rxjava的网络框架，自己也动手试着搭建了下，不得不吐槽一波！坑~坑-坑真的是坑！

因为度娘给的都是之前的老版本的集成方法，鄙人按照之前的方法去配置，各种Crash。后来，在大google和自己瞎倒腾下终于是把请求给走通了。下面罗列一下搭建过程（Android studio 搭建）。
## retrofit相关配置
### 使用的版本
1. ` retrofit`：2.2.0
2. ` rxandroid`：2.0.1
3. ` rxjava`：2.0.9
### build.gradle文件

```
compile 'com.squareup.retrofit2:retrofit:2.2.0'
compile 'com.squareup.retrofit2:converter-gson:2.2.0'
compile 'com.squareup.retrofit2:retrofit-adapters:2.2.0'
compile 'com.squareup.retrofit2:adapter-rxjava2:2.2.0'
compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
// Because RxAndroid releases are few and far between, it is recommended you also
// explicitly depend on RxJava's latest version for bug fixes and new features.
compile 'io.reactivex.rxjava2:rxjava:2.0.9'

```
**注意上面第四行的` adapter-rxjava2:2.2.0`中一定写上rxjava==2==，老的博客里面都是配置的是的是rxjava**这里也是遇到各种坑，不写上2的话会报` Unable to create call adapter for io.reactivex.Observable`的异常导致闪退
### AndroidManifest.xml配置

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />

```
## 步入正题，retrofit的初始化以及demo
### 初始化retrofit
直接上代码

```
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
                baseUrl("http://ent.sipmch.com.cn").
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build();
        mHttpInterface = retrofit.create(HttpInterface.class);
    }
}

```
定义接口

```
public interface HttpInterface {
    @FormUrlEncoded
    @POST("/ModuleDefaultCompany/RentManage/SearchRentNo/")
    Observable<RoomResult> search(@Field("CertNo") String idNo);
}
```
返回结果的JsonBean

```
public class RoomResult {
    public String result;
    public String prompWord;
}
```

方法调用

```
                        Apiservice.getService().search(account)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(roomResult -> {
                                Utils.showToast(mActivity,roomResult.prompWord);
                            });

```
好了大功告成。
## 其它
最近也是自己在研究App框架的搭建，也是准备做个一个全新的App Demo出来。现在采用的是的MVP+retrofit+rxjava+greenDao+butterKnif

非常感谢大家的反馈和建议：

QQ：2280885690

邮箱：2280885690@q.com
