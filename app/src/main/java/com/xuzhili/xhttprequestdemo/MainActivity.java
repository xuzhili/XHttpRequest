package com.xuzhili.xhttprequestdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xuzhili.xhttprequest.XHttpFactory;
import com.xuzhili.xhttprequest.XHttpManager;
import com.xuzhili.xhttprequest.carry.XHttpRequester;
import com.xuzhili.xhttprequest.http.config.DefaultHttpConfig;
import com.xuzhili.xhttprequest.interceptor.IRequestInterceptor;
import com.xuzhili.xhttprequest.interceptor.IRequestRetryInterceptor;
import com.xuzhili.xhttprequest.interceptor.IResponseInterceptor;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initXHttp();

        Uri parse = Uri.parse("http://cdn.weather.hao.360.cn/api_weather_info.php?app=hao360&_jsonp=smartloaddata101190101&code=101190101");
        Log.d("MainActivity", "path:" + parse.getPath() + ":getScheme:"
                + parse.getScheme() + ":getAuthority:" + parse.getAuthority()
                + ":getHost:" + parse.getHost() + ":getQuery:" + parse.getQuery());

        createRequest();
    }

    private void createRequest() {

        XHttpFactory.createRequester("http://cdn.weather.hao.360.cn/api_weather_info.php")
                .get()
                .addParameter("app", "hao360")
                .addParameter("code", "101190101")
                .compose(HttpResponse.class)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResponse httpResponse) {
                        Log.d(TAG, "httpResponse:" + httpResponse.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:");
                    }
                });

    }

    private void initXHttp() {
        XHttpManager.getXHttpManager()
                .addRequestInterceptor(new IRequestInterceptor() {
                    @Override
                    public <T> void onInterceptor(@NonNull XHttpRequester xHttpRequester, @NonNull Class<T> responseClass) {
                        xHttpRequester.addHeader("Accept-Encoding", "gzip");
                        xHttpRequester.addHeader("Content-Type", "application/json;charset=utf-8");
                    }
                })
                .addRequestInterceptor(new IRequestInterceptor() {
                    @Override
                    public <T> void onInterceptor(@NonNull XHttpRequester xHttpRequester, @NonNull Class<T> responseClass) {

                    }
                })
                .addResponseInterceptor(new IResponseInterceptor() {
                    @Override
                    public void onResponse(@NonNull XHttpRequester xHttpRequester, @NonNull String response) {
                        Log.d(TAG, "response:" + response);
                    }
                })
                .addRequestRetryInterceptor(new IRequestRetryInterceptor() {
                    @Override
                    public void onRequestRetry(@NonNull XHttpRequester xHttpRequester, @NonNull Throwable reason, int requestCount) {
                        String url = xHttpRequester.getUrl();
                        //you can change your host for url to retry
//                        xHttpRequester.url(url);
                        Log.d(TAG, "count:" + requestCount + ":" + url);
                    }
                })
                .setHttpConfig(new DefaultHttpConfig())
                .setDebug(true);
    }
}
