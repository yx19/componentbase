package com.smartlab.componentbase.http;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhy.http.okhttp.intercepter.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * @author：xxl
 * @Created in：2019/4/3
 */
public class RetrofitHelper {
    private static Retrofit retrofit;
    private static HashSet<String> hashSet;
    private static String url = "http://47.105.219.66:8086/";

//    private static String url = "http://192.168.1.103:8086/";
//    private static String url = "http://192.168.1.117:8086/";
//    private static String url = "http://192.168.8.113:8082/";

    private static Retrofit create() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("retrofit");
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //获取请求头并修改
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        if (hashSet != null) {
                            for (String cookie : hashSet) {
                                builder.addHeader("x-auth-token", cookie);
                            }
                        }
                        return chain.proceed(builder.build());
                    }
                })

                //获取响应头并保存
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        if (!originalResponse.headers("x-auth-token").isEmpty()) {
                            HashSet<String> cookies = new HashSet<>();
                            for (String header : originalResponse.headers("x-auth-token")) {
                                cookies.add(header);
                            }
                            hashSet = cookies;
                            Log.e("info1234", hashSet.toString());
                        }
                        return originalResponse;
                    }
                })
                .addInterceptor(loggingInterceptor)//日志拦截器
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = create();
        }
        return retrofit;
    }

    public static String getUrl() {
        return url;
    }
}
