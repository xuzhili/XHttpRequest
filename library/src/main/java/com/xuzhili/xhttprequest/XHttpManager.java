package com.xuzhili.xhttprequest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xuzhili.xhttprequest.interceptor.IRequestInterceptor;
import com.xuzhili.xhttprequest.interceptor.IResponseInterceptor;

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

    public static XHttpManager getxHttpManager() {
        return xHttpManager;
    }

    private XHttpManager addRequestInterceptor(@NonNull IRequestInterceptor iRequestInterceptor) {
        if (iRequestInterceptors == null) {
            iRequestInterceptors = new ArrayList<>();
        }
        iRequestInterceptors.add(iRequestInterceptor);
        return this;
    }

    private XHttpManager addResponseInterceptor(@NonNull IResponseInterceptor iResponseInterceptor) {
        if (iResponseInterceptors == null) {
            iResponseInterceptors = new ArrayList<>();
        }
        iResponseInterceptors.add(iResponseInterceptor);
        return this;
    }

    @Nullable
    public List<IRequestInterceptor> getiRequestInterceptors() {
        return iRequestInterceptors;
    }

    @Nullable
    public List<IResponseInterceptor> getiResponseInterceptors() {
        return iResponseInterceptors;
    }
}
