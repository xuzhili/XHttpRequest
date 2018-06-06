package com.xuzhili.xhttprequest.http;

import com.xuzhili.xhttprequest.XHttpManager;
import com.xuzhili.xhttprequest.http.config.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by $xuzhili on 2018/6/4.
 * hnzkxuzhili@gmail.com
 */

public final class XHttpClientBuilder {

    private static OkHttpClient defaultOkHttpClient;

    public static OkHttpClient create(HttpConfig httpConfig) {
        return getHttpClient()
                .newBuilder()
                .readTimeout(httpConfig.getReadTimeOut(), TimeUnit.SECONDS)
                .writeTimeout(httpConfig.getWriteTimeOut(), TimeUnit.SECONDS)
                .connectTimeout(httpConfig.getConnectTimeOut(), TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getHttpClient() {
        if (defaultOkHttpClient == null) {
            HttpConfig httpConfig = XHttpManager.getXHttpManager().getHttpConfig();
            defaultOkHttpClient = new OkHttpClient()
                    .newBuilder()
                    .readTimeout(httpConfig.getReadTimeOut(), TimeUnit.SECONDS)
                    .writeTimeout(httpConfig.getWriteTimeOut(), TimeUnit.SECONDS)
                    .connectTimeout(httpConfig.getConnectTimeOut(), TimeUnit.SECONDS)
                    .build();
        }
        return defaultOkHttpClient;
    }
}
