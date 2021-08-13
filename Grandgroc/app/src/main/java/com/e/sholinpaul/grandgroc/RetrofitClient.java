package com.e.sholinpaul.grandgroc;


import androidx.annotation.NonNull;

import com.e.sholinpaul.grandgroc.cloud.API_Grand_Groc;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient mRetrofitClient;

    public static String BASE_URL =  BuildConfig.BASE_URL;;


    public static RetrofitClient getInstance() {
        if (mRetrofitClient == null) {
            mRetrofitClient = new RetrofitClient();
        }
        return mRetrofitClient;
    }

    @NonNull
    private Retrofit getRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // final OkHttpClient httpClient = new OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES);


//        httpClient.addInterceptor(new Interceptor() {
//            @NonNull
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Accept", "application/json")
//                        .addHeader("auth", "com.diplomat.android.client")
//                        .build();
//                //Compare the above auth in server side
//                return chain.proceed(request);
//            }
//        });
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }


    public API_Grand_Groc getLoginClient() {

        Retrofit retrofit = getRetrofit();

        return retrofit.create(API_Grand_Groc.class);
    }
//
//    public API_EMP getEmpClient() {
//
//        Retrofit retrofit = getRetrofit();
//
//        return retrofit.create(API_EMP.class);
//    }


}
