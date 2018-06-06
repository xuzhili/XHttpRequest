package com.xuzhili.xhttprequest.exception;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * Created by $xuzhili on 2018/6/5.
 * hnzkxuzhili@gmail.com
 */

public final class XExceptionUtil {

    /**
     * 是否是网络异常
     */
    public static boolean isNetworkError(Throwable throwable) {
        return (throwable instanceof UnknownHostException) || (throwable instanceof TimeoutException)
                || (throwable instanceof SocketException) || (throwable instanceof SocketTimeoutException);
    }
}
