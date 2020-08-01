package com.hans.xlib.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @date: 2019-12-04 16:49
 * @author: drq
 * @description null
 */
public class RetrofitHelper {

    private static OkHttpClient client;
    private static RetrofitHelper sInstace;
    private static Retrofit retrofit;

    /**
     * 创建单例
     */
    public static RetrofitHelper getInstance() {
        if (sInstace == null) {
            synchronized (RetrofitHelper.class) {
                sInstace = new RetrofitHelper();
            }
        }
        return sInstace;
    }

    private RetrofitHelper() {

        client = new OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();

        // 获取retrofit的实例
        retrofit = new Retrofit
                .Builder()
                .baseUrl("http://www.baidu.com")  //自己配置
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
