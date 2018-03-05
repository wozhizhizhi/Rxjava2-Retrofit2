package com.aji.retrofitlib.net;

import com.aji.retrofitlib.util.Constants;
import com.aji.retrofitlib.util.LogUtils;
import com.aji.retrofitlib.util.NetworkUtils;
import com.aji.retrofitlib.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:zhangmiss on 2018/03/05 0005.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class IcApi
{
    private IcApiService icApiService;
    private Retrofit retrofit;

    public IcApi()
    {
        // 日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger()
        {
            @Override
            public void log(String message)
            {
                try
                {
                    String text = URLDecoder.decode(message, "utf-8");
                    LogUtils.e("OKHttp-----", text);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    LogUtils.e("OKHttp-----", message);
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File fileCache = new File(Utils.getContext().getCacheDir() , "httpCache");
        // 150M缓存
        Cache cache = new Cache(fileCache , 1024 * 1024 * 150);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(Constants.DEFAULT_TIMEOUT , TimeUnit.MILLISECONDS)
                                                            .connectTimeout(Constants.DEFAULT_TIMEOUT , TimeUnit.MILLISECONDS)
                                                            .addInterceptor(httpLoggingInterceptor)
                                                            .addInterceptor(new HttpHeaderInterceptor())
                                                            .addNetworkInterceptor(new HttpCacheInterceptor())
                                                            .cache(cache)
                                                            .build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.API_SERVER_URL)
                .build();

        icApiService = retrofit.create(IcApiService.class);

    }

    // 创建该实例的单例
    private static class SingletonHolder
    {
        private static final IcApi INSTANCE = new IcApi();
    }

    public static IcApiService getIcApiService()
    {
        return SingletonHolder.INSTANCE.icApiService;
    }

    //  添加请求头的拦截器
    private class HttpHeaderInterceptor implements Interceptor
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {
            //  配置请求头
            String accessToken = "token";
            String tokenType = "tokenType";
            Request request = chain.request().newBuilder()
                    .header("app_key","appId")
                    .header("Authorization", tokenType + " " + accessToken)
                    .header("Content-Type", "application/json")
                    .addHeader("Connection", "close")
                    .build();
            return chain.proceed(request);
        }
    }

    class HttpCacheInterceptor implements Interceptor
    {
        @Override
        public Response intercept(Chain chain) throws IOException
        {
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected())
            {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
            else
            {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
