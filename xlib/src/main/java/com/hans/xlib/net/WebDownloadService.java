package com.hans.xlib.net;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @date: 2019-12-04 16:45
 * @author: drq
 * @description null
 */
public interface WebDownloadService {

    @Streaming
    @GET
    Observable<Response<ResponseBody>> rxDownload(@Url String url);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);

    @GET
    Observable<Object> getHomeParams(@Url String url);




}
