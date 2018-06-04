package com.xuzhili.xhttprequest.http;

import okhttp3.OkHttpClient;

/**
 * Created by $xuzhili on 2018/6/4.
 * hnzkxuzhili@gmail.com
 */

public final class OkHttpClientBuilder {

    public OkHttpClient build() {

    }

    private OkHttpClient create() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor()
    }

}
