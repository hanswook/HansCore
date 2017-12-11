package com.hans.hc.hanscore;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by hans
 * date: 2017/12/11 15:08.
 * e-mail: hxxx1992@163.com
 */

public interface ApiService {
    @GET("/api/data/Android/10/1")
    Observable<GankB> getGankData();

}
