package com.xuzhili.xhttprequest.carry;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xuzhili.xhttprequest.XHttpManager;
import com.xuzhili.xhttprequest.exception.XExceptionUtil;
import com.xuzhili.xhttprequest.http.XHttpClientBuilder;
import com.xuzhili.xhttprequest.http.XRequestBuilder;
import com.xuzhili.xhttprequest.http.XRequestFileBody;
import com.xuzhili.xhttprequest.http.config.HttpConfig;
import com.xuzhili.xhttprequest.interceptor.IRequestInterceptor;
import com.xuzhili.xhttprequest.interceptor.IRequestRetryInterceptor;
import com.xuzhili.xhttprequest.interceptor.IResponseInterceptor;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiPredicate;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.GzipSource;
import okio.Okio;

/**
 * Created by $xuzhili on 2018/6/5.
 * hnzkxuzhili@gmail.com
 */

public class XFunctionObserver {

    public static final String TAG = XFunctionObserver.class.getSimpleName();

    public <T> Observable<T> observable(final XHttpRequester xHttpRequester, final Class<T> responseClass) {

        return Observable
                .defer(new Callable<ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> call() throws Exception {
                        XRequestBuilder xRequestBuilder = new XRequestBuilder();
                        xRequestBuilder.url(xHttpRequester.getUrl());
                        InputStreamReader inputStreamReader = null;
                        ResponseBody responseBody = null;
                        T responseEntity = null;
                        try {
                            //add header
                            addRequestHeaders(xRequestBuilder, xHttpRequester);

                            //请求拦截器
                            callbackRequestInterceptor(xHttpRequester, responseClass);

                            if (xHttpRequester.getFileParameters() != null) {
                                xHttpRequester.file();
                            }

                            //入参
                            addParams(xRequestBuilder, xHttpRequester);

                            OkHttpClient httpClient = XHttpClientBuilder.getHttpClient();
                            HttpConfig httpConfig = xHttpRequester.getHttpConfig();
                            if (httpConfig != null) {
                                httpClient = XHttpClientBuilder.create(httpConfig);
                            }
                            Response response = httpClient.newCall(xRequestBuilder.build()).execute();

                            responseBody = response.body();
                            byte[] responseBytes;
                            if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
                                responseBytes = Okio.buffer(new GzipSource(responseBody.source())).readByteArray();
                            } else {
                                responseBytes = responseBody.bytes();
                            }

                            //请求结果拦截器
                            callbackResponseInterceptor(responseBytes, xHttpRequester);

                            inputStreamReader = new InputStreamReader(new ByteArrayInputStream(responseBytes));
                            responseEntity = xHttpRequester.getGson().fromJson(inputStreamReader,
                                    responseClass);
                        } finally {
                            close(responseBody, inputStreamReader);
                        }
                        return Observable.just(responseEntity);
                    }
                })
                .retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(Integer integer, Throwable throwable) throws Exception {
                        HttpConfig httpConfig = xHttpRequester.getHttpConfig();
                        if (httpConfig == null) {
                            httpConfig = XHttpManager.getXHttpManager().getHttpConfig();
                        }
                        callbackRequestRetry(integer, throwable, xHttpRequester);
                        boolean retry = integer <= httpConfig.getRetryCount() && XExceptionUtil.isNetworkError(throwable);
                        if (XHttpManager.getXHttpManager().isDebug()) {
                            Log.e(TAG, "retryRequest:" + integer + ":" + xHttpRequester.getUrl(), throwable);
                        }
                        return retry;
                    }
                });


    }

    private static void callbackResponseInterceptor(byte[] responseBytes, XHttpRequester xHttpRequester) {
        List<IResponseInterceptor> iResponseInterceptors = XHttpManager.getXHttpManager().getIResponseInterceptors();
        if (iResponseInterceptors != null) {
            String responseString = new String(responseBytes);
            for (IResponseInterceptor iResponseInterceptor : iResponseInterceptors) {
                iResponseInterceptor.onResponse(xHttpRequester, responseString);
            }
        }
    }

    private static void addParams(XRequestBuilder xRequestBuilder, XHttpRequester xHttpRequester) {
        //入参
        Map<String, String> submitParams = getSubmitParams(xHttpRequester);

        switch (xHttpRequester.getRequestMethod()) {
            case XHttpRequester.METHOD_GET:
                addParametersForGet(xRequestBuilder, xHttpRequester, submitParams);
                break;
            case XHttpRequester.METHOD_FILE:
                addParametersForFiles(xRequestBuilder, submitParams, xHttpRequester.getFileParameters());
                break;
            case XHttpRequester.METHOD_POST:
                addParametersForPost(xRequestBuilder, submitParams);
                break;
            default:
                break;
        }
    }

    private static void callbackRequestRetry(Integer integer, Throwable throwable, XHttpRequester xHttpRequester) {
        List<IRequestRetryInterceptor> iRequestRetryInterceptors = XHttpManager.getXHttpManager().getIRequestRetryInterceptors();
        if (iRequestRetryInterceptors != null) {
            for (IRequestRetryInterceptor iRequestRetryInterceptor : iRequestRetryInterceptors) {
                iRequestRetryInterceptor.onRequestRetry(xHttpRequester, throwable, integer);
            }
        }
    }

    @Nullable
    private static Map<String, String> getSubmitParams(XHttpRequester xHttpRequester) {
        Map<String, String> submitParameters = xHttpRequester.getSubmitParameters();
        if (submitParameters != null) {
            return submitParameters;
        } else {
            return xHttpRequester.getOriginParameters();
        }
    }

    private static <T> void callbackRequestInterceptor(XHttpRequester xHttpRequester, Class<T> responseClass) {
        List<IRequestInterceptor> iRequestInterceptors = XHttpManager.getXHttpManager().getIRequestInterceptors();
        if (iRequestInterceptors != null) {
            for (IRequestInterceptor iRequestInterceptor : iRequestInterceptors) {
                iRequestInterceptor.onInterceptor(xHttpRequester, responseClass);
            }
        }
    }

    private static void addRequestHeaders(XRequestBuilder XRequestBuilder, XHttpRequester xHttpRequester) {
        Map<String, String> originHeaders = xHttpRequester.getOriginHeaders();
        if (originHeaders == null) {
            return;
        }
        Set<String> headersKeySet = originHeaders.keySet();
        for (String next : headersKeySet) {
            XRequestBuilder.addHeader(next, originHeaders.get(next));
        }
    }

    private static void addParametersForGet(XRequestBuilder builder, XHttpRequester xHttpRequester,
                                            Map<String, String> submitParams) {
        if (submitParams == null) {
            return;
        }
        Uri.Builder uriBuilder = Uri.parse(xHttpRequester.getUrl()).buildUpon();
        for (String key : submitParams.keySet()) {
            uriBuilder.appendQueryParameter(key, submitParams.get(key));
        }
        builder.url(uriBuilder.build().toString());
    }

    private static void addParametersForPost(XRequestBuilder builder, Map<String, String> submitParams) {
        if (submitParams == null) {
            return;
        }
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (String key : submitParams.keySet()) {
            bodyBuilder.add(key, submitParams.get(key));
        }
        builder.post(bodyBuilder.build());
    }

    private static void addParametersForFiles(XRequestBuilder builder, Map<String, String> submitParams,
                                              Map<String, XRequestFileBody> fileParams) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (submitParams != null) {
            for (String key : submitParams.keySet()) {
                bodyBuilder.addFormDataPart(key, submitParams.get(key));
            }
        }

        if (fileParams != null) {
            for (String key : fileParams.keySet()) {
                XRequestFileBody xRequestFileBody = fileParams.get(key);
                bodyBuilder.addFormDataPart(key, xRequestFileBody.getFileName(), xRequestFileBody.getRequestBody());
            }
        }

        builder.post(bodyBuilder.build());
    }

    private static void close(Closeable... closeables) {
        if (null == closeables) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
