package com.mckj.tec_library.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class HttpUrlManager {
    private static final Map<HttpManager.Retrofits, String> RETROFITS_URL_MAP = new HashMap<>();

    static void init(Context context) {
            RETROFITS_URL_MAP.put(HttpManager.Retrofits.base, UrlConstant.BASE_URL);
//        try {
//            RETROFITS_URL_MAP.put(HttpManager.Retrofits.base, MetaDataUtils.getString(context, "BASE_URL"));
//        } catch (PackageManager.NameNotFoundException e) {
//            LogUtil.d(e);
//        }

    }

    public static String getUrl(HttpManager.Retrofits retrofits) {
        return RETROFITS_URL_MAP.get(retrofits);
    }

}