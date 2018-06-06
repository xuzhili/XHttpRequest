package com.xuzhili.xhttprequest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xuzhili.xhttprequest.http.config.DefaultHttpConfig;
import com.xuzhili.xhttprequest.http.config.HttpConfig;
import com.xuzhili.xhttprequest.interceptor.IRequestInterceptor;
import com.xuzhili.xhttprequest.interceptor.IResponseInterceptor;
import com.xuzhili.xhttprequest.interceptor.IRequestRetryInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by $xuzhili on 2018/6/4.
 * hnzkxuzhili@gmail.com
 */

public final class XHttpManager {

    private static final XHttpManager xHttpManager;

    static {
        xHttpManager = new XHttpManager();
    }

    private List<IRequestInterceptor> iRequestInterceptors;
    private List<IResponseInterceptor> iResponseInterceptors;
    private List<IRequestRetryInterceptor> iRequestRetryInterceptors;
    private HttpConfig httpConfig;
    private boolean isDebug;

    public static XHttpManager getXHttpManager() {
        return xHttpManager;
    }

    public XHttpManager addRequestInterceptor(@NonNull IRequestInterceptor iRequestInterceptor) {
        if (iRequestInterceptors == null) {
            iRequestInterceptors = new ArrayList<>();
        }
        iRequestInterceptors.add(iRequestInterceptor);
        return this;
    }

    public XHttpManager addResponseInterceptor(@NonNull IResponseInterceptor iResponseInterceptor) {
        if (iResponseInterceptors == null) {
            iResponseInterceptors = new ArrayList<>();
        }
        iResponseInterceptors.add(iResponseInterceptor);
        return this;
    }

    public XHttpManager addRequestRetryInterceptor(@NonNull IRequestRetryInterceptor iRequestRetryInterceptor) {
        if (iRequestRetryInterceptors == null) {
            iRequestRetryInterceptors = new ArrayList<>();
        }
        iRequestRetryInterceptors.add(iRequestRetryInterceptor);
        return this;
    }

    @Nullable
    public List<IRequestRetryInterceptor> getIRequestRetryInterceptors() {
        return iRequestRetryInterceptors;
    }

    @Nullable
    public List<IRequestInterceptor> getIRequestInterceptors() {
        return iRequestInterceptors;
    }

    @Nullable
    public List<IResponseInterceptor> getIResponseInterceptors() {
        return iResponseInterceptors;
    }

    @NonNull
    public HttpConfig getHttpConfig() {
        if (httpConfig == null){
            httpConfig = new DefaultHttpConfig();
        }
        return httpConfig;
    }

    public XHttpManager setHttpConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
        return this;
    }

    public XHttpManager setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }
}
