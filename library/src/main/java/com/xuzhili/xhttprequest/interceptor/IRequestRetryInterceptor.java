package com.xuzhili.xhttprequest.interceptor;

import android.support.annotation.NonNull;

import com.xuzhili.xhttprequest.carry.XHttpRequester;

/**
 * Created by $xuzhili on 2018/6/6.
 * hnzkxuzhili@gmail.com
 */

public interface IRequestRetryInterceptor {

    void onRequestRetry(@NonNull XHttpRequester xHttpRequester, @NonNull Throwable reason, int requestCount);

}
