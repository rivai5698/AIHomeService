package com.example.aihomeservice.connect;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkProvider {
    private static volatile NetworkProvider mInstance = null;
    private final String BASE_URL = "https://api.nextiva.vn/";
    private final Retrofit retrofit;

    private NetworkProvider() {
//      OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//      @Override
//      public Response intercept(Chain chain) throws IOException {
//        Request newRequest  = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer " + tokenString)
//            .build();
//        return chain.proceed(newRequest);
//      }
//    }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
    }
    public static NetworkProvider self() {
        if (mInstance == null)
            mInstance = new NetworkProvider();
        return mInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
