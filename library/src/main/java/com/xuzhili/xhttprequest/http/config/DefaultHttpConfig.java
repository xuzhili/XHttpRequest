package com.xuzhili.xhttprequest.http.config;

/**
 * Created by $xuzhili on 2018/6/5.
 * hnzkxuzhili@gmail.com
 */

public class DefaultHttpConfig extends HttpConfig {

    public DefaultHttpConfig() {
        setConnectTimeOut(REQUEST_TIMEOUT)
                .setReadTimeOut(REQUEST_TIMEOUT)
                .setWriteTimeOut(REQUEST_TIMEOUT)
                .setRetryCount(REQUEST_RETRY_COUNT);
    }
}
