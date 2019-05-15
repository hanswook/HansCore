package com.mckj.tec_library.http;

import android.content.Context;


import java.io.IOException;

import com.mckj.tec_library.base.DebugConstant;
import com.mckj.tec_library.utils.LogUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkIOInterceptor implements Interceptor {
    private Context mContext;

    public NetworkIOInterceptor(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Connection", "close").build();
        Response response;
        try {
            response = chain.proceed(request);
            if (!response.isSuccessful()) {
                // TODO
            }
            // TODO
        } catch (IOException e) { // 对业务层屏蔽掉一些网络错误
            if (DebugConstant.DEBUG) {
                LogUtil.d(e);
            }
            throw new IOException(e.getMessage());
        }
        return response;
    }
}
