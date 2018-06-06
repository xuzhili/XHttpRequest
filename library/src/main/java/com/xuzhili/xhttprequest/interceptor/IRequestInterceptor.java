package com.xuzhili.xhttprequest.interceptor;

import android.support.annotation.NonNull;

import com.xuzhili.xhttprequest.carry.XHttpRequester;

/**
 * Created by $xuzhili on 2018/6/4.
 * hnzkxuzhili@gmail.com
 */

public interface IRequestInterceptor {

    <T> void onInterceptor(@NonNull XHttpRequester xHttpRequester, @NonNull Class<T> responseClass);

}
