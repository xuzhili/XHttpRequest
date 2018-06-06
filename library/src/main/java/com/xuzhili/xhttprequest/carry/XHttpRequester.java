package com.xuzhili.xhttprequest.carry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.xuzhili.xhttprequest.http.XRequestFileBody;
import com.xuzhili.xhttprequest.http.config.HttpConfig;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;

/**
 * Created by $xuzhili on 2018/6/5.
 * hnzkxuzhili@gmail.com
 */

public class XHttpRequester {

    public static final String METHOD_GET = "Get";
    public static final String METHOD_POST = "Post";
    public static final String METHOD_FILE = "File";

    private String url;

    private String requestMethod = METHOD_GET;

    private Map<String, String> originParameters;
    private Map<String, String> originHeaders;
    private Map<String, String> submitParameters;
    private Map<String, XRequestFileBody> fileParameters;
    private Gson gson;
    private HttpConfig httpConfig;

    public XHttpRequester(String url) {
        this.url = url;
    }

    public XHttpRequester url(String url) {
        this.url = url;
        return this;
    }

    public XHttpRequester get() {
        requestMethod = METHOD_GET;
        return this;
    }

    public XHttpRequester post() {
        requestMethod = METHOD_POST;
        return this;
    }

    public XHttpRequester file() {
        requestMethod = METHOD_FILE;
        return this;
    }

    public XHttpRequester config(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
        return this;
    }

    public XHttpRequester addHeader(@NonNull String key, @Nullable Object value) {
        if (value == null) {
            value = "";
        }
        if (originHeaders == null) {
            originHeaders = new TreeMap<>();
        }
        originHeaders.put(key, String.valueOf(value));
        return this;
    }

    public XHttpRequester addParameter(@NonNull String key, @Nullable Object value) {
        if (value == null) {
            value = "";
        }
        if (originParameters == null) {
            originParameters = new TreeMap<>();
        }
        originParameters.put(key, String.valueOf(value));
        return this;
    }

    public XHttpRequester addSubmitParameter(@NonNull String key, @Nullable Object value) {
        if (value == null) {
            value = "";
        }
        if (submitParameters == null) {
            submitParameters = new TreeMap<>();
        }
        submitParameters.put(key, String.valueOf(value));
        return this;
    }

    public XHttpRequester addFileParameter(String name, File file) {
        if (null == fileParameters) {
            fileParameters = new TreeMap<>();
        }
        fileParameters.put(name, new XRequestFileBody(file));
        return this;
    }

    public XHttpRequester gson(Gson gson) {
        this.gson = gson;
        return this;
    }

    @Nullable
    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    @NonNull
    public Gson getGson() {
        return gson == null ? new Gson() : gson;
    }

    public <T> Observable<T> compose(Class<T> responseClass) {
        return new XFunctionObserver().observable(this, responseClass);
    }

    @Nullable
    public Map<String, String> getOriginHeaders() {
        return originHeaders;
    }

    @Nullable
    public Map<String, String> getOriginParameters() {
        return originParameters;
    }

    @Nullable
    public Map<String, XRequestFileBody> getFileParameters() {
        return fileParameters;
    }

    @Nullable
    public Map<String, String> getSubmitParameters() {
        return submitParameters;
    }


    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

}
