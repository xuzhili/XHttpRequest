package com.xuzhili.xhttprequest.http.config;

/**
 * Created by $xuzhili on 2018/6/5.
 * hnzkxuzhili@gmail.com
 */

public class HttpConfig {

    static final int REQUEST_RETRY_COUNT = 2;
    static final int REQUEST_TIMEOUT = 60;

    /**
     * seconds
     */
    private Integer readTimeOut;
    /**
     * seconds
     */
    private Integer writeTimeOut;
    /**
     * seconds
     */
    private Integer connectTimeOut;

    private Integer retryCount;

    public int getReadTimeOut() {
        return readTimeOut != null ? readTimeOut : REQUEST_TIMEOUT;
    }

    public HttpConfig setReadTimeOut(Integer readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public int getWriteTimeOut() {
        return writeTimeOut != null ? writeTimeOut : REQUEST_TIMEOUT;
    }

    public HttpConfig setWriteTimeOut(Integer writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }


    public int getConnectTimeOut() {
        return connectTimeOut != null ? connectTimeOut : REQUEST_TIMEOUT;
    }

    public HttpConfig setConnectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return this;
    }

    public HttpConfig setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public int getRetryCount() {
        return retryCount != null ? retryCount : REQUEST_RETRY_COUNT;
    }
}
